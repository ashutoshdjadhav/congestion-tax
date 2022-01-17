package com.volvo.service;

import com.volvo.bean.TaxCalculationRequest;
import com.volvo.dao.HolidaysDao;
import com.volvo.dao.TaxRateDao;
import com.volvo.dao.VehicleExemptDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.volvo.validator.TaxCalculatorValidation.isNotWeekend;

/*
* This class is used to calculate congestion Tax
* */
@Service
public class TaxCalculatorService {

    @Autowired
    HolidaysDao holidaysDao;

    @Autowired
    VehicleExemptDao vehicleExemptDao;

    @Autowired
    TaxRateDao taxRateDao;

    @Value("${perDay.max.tax:60}")
    private Integer maxTaxPerDay;

    @Value("${taxed.once.minutes:60}")
    private Integer taxedOnceMinutes;

    /*
    * This method will calculate tax depending on request parameter
    * */
    public String getTaxAmount(TaxCalculationRequest request) {
        double totalTax = 0;
        if(!vehicleExemptDao.isVehicleTaxExempt(request.getVehicleType())) {
            Map<LocalDate, List<LocalDateTime>> collect = getLocalDateListByGroup(request);
            for (LocalDate key : collect.keySet()) {
                if(isNotWeekend(key) && isNotPublicHoliday(key) && isNotDayBeforePublicHoliday(key)) {
                    List<LocalDateTime> list = collect.get(key);
                    if (list.size() > 0) {
                        totalTax += calculateTaxAmount(list);
                    }
                }
            }
        }
        return "Total Tax: " + totalTax;
    }

    private double calculateTaxAmount(List<LocalDateTime> list) {
        Optional<LocalTime> startTime = Optional.empty();
        double dayTotalTax = 0;
        double maxAmount = 0;
        for (LocalDateTime currentDateTime : list) {
            double taxAmount = taxRateDao.getTaxAmountByTime(currentDateTime.toLocalTime());
            if(startTime.isPresent()) {
                long minuteDifference = startTime.get().until(currentDateTime.toLocalTime(), ChronoUnit.MINUTES);
                if(minuteDifference < taxedOnceMinutes) {
                    if(maxAmount < taxAmount) {
                        maxAmount = taxAmount;
                    }
                } else {
                    startTime = Optional.of(currentDateTime.toLocalTime());
                    dayTotalTax += maxAmount;
                    maxAmount = taxAmount;
                }
            } else {
                startTime = Optional.of(currentDateTime.toLocalTime());
                maxAmount = taxAmount;
            }
        }
        dayTotalTax += maxAmount;
        dayTotalTax = getMaxTaxAmount(dayTotalTax);
        return dayTotalTax;
    }

    private double getMaxTaxAmount(double dayTotalTax) {
        if(dayTotalTax > maxTaxPerDay) {
            dayTotalTax = maxTaxPerDay;
        }
        return dayTotalTax;
    }

    private boolean isNotPublicHoliday(LocalDate localDate) {
        return !holidaysDao.isPublicHoliday(localDate.getMonth().name(), localDate.getDayOfMonth());
    }

    private boolean isNotDayBeforePublicHoliday(LocalDate localDate) {
        return !holidaysDao.isPublicHoliday(localDate.getMonth().name(), localDate.plusDays(1).getDayOfMonth());
    }

    private Map<LocalDate, List<LocalDateTime>> getLocalDateListByGroup(TaxCalculationRequest request) {
        Collections.sort(request.getTollDateTimeList());
        return request.getTollDateTimeList().stream().collect(Collectors.groupingBy(a -> a.toLocalDate()));
    }
}
