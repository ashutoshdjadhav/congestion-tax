package com.volvo.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

/*
* This class is used to fetch Exempt vehicle details
* */
@Repository
public class VehicleExemptDao extends DAO{

    private List<String> vehicleExemptList;

    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleExemptDao.class);

    public VehicleExemptDao() throws IOException {
        super();
        vehicleExemptList = objectMapper.readValue(new File(FILE_PATH + "VehicleExempt.json"), List.class);
    }

    /*
    * This method will check that given vehicle is tax exempt or not
    * */
    public boolean isVehicleTaxExempt(String vehicleName) {
        LOGGER.info("Checking given vehicle is under Tax Exempt or not. Vehicle Name: " + vehicleName);
        return vehicleExemptList.contains(vehicleName.toLowerCase());
    }
}
