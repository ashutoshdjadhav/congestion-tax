package com.volvo.controller;

import com.volvo.bean.TaxCalculationRequest;
import com.volvo.service.TaxCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/tax")
public class TaxCalculatorController {

    @Autowired
    TaxCalculatorService service;

    @RequestMapping(value = "calculate", method = RequestMethod.GET)
    public String getTaxAmount(@RequestBody TaxCalculationRequest request) {
        return service.getTaxAmount(request);
    }
}
