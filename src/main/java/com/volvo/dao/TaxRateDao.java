package com.volvo.dao;

import com.volvo.bean.TaxRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

/*
* This class is used to Fetch Tax Rate
* */
@Repository
public class TaxRateDao extends DAO {

    private List<TaxRate> taxRateList;

    private static final Logger LOGGER = LoggerFactory.getLogger(TaxRateDao.class);

    public TaxRateDao() throws IOException {
        super();
        taxRateList = Arrays.asList(objectMapper.readValue(new File(FILE_PATH + "TaxRates.json"), TaxRate[].class));
        taxRateList.sort(Comparator.comparing(TaxRate::getStartTime));
    }

    /*
    * This method will return tax amount for given time
    *
    * */
    public double getTaxAmountByTime(LocalTime time) {
        LOGGER.info("Fetching Tax amount for Time: " + time);
        Optional<TaxRate> taxRate = taxRateList.stream().filter(getTaxRatePredicate(time)).findFirst();
        if(!taxRate.isPresent()) {
          taxRate = Optional.of(taxRateList.get(taxRateList.size() - 1));
        }
        return taxRate.get().getAmount();
    }

    private Predicate<TaxRate> getTaxRatePredicate(LocalTime time) {
        return a -> (a.getStartTime().isBefore(time) || a.getStartTime().equals(time))
                && (a.getEndTime().isAfter(time)) || a.getEndTime().equals(time);
    }
}
