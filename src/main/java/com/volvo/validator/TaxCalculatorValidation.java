package com.volvo.validator;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

/*
* This class is used to validate Tax rules
* */
public class TaxCalculatorValidation {

    /*
    * This method will check that given date is weekend or not
    * */
    public static boolean isNotWeekend(LocalDate localDateTime) {
        DayOfWeek day = DayOfWeek.of(localDateTime.get(ChronoField.DAY_OF_WEEK));
        return !(day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY);
    }
}
