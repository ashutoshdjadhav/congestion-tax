package com.volvo.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* This class is used fetch all holiday details
* */
@Repository
public class HolidaysDao extends DAO {
    private Map<String, List<Integer>> holidayDetails;

    private static final Logger LOGGER = LoggerFactory.getLogger(HolidaysDao.class);

    public HolidaysDao() throws IOException {
        super();
        holidayDetails = objectMapper.readValue(new File(FILE_PATH + "Holidays.json"), HashMap.class);
    }

    /*
    * This method will check that given day is holiday or not
    * */
    public boolean isPublicHoliday(String month, Integer day) {
        LOGGER.info("Checking given day is public holiday or not. Month: " + month +", day:" + day);
        return holidayDetails.get(month.toLowerCase()).contains(day);
    }

}
