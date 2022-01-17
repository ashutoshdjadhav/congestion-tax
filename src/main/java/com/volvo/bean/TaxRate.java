package com.volvo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaxRate {

    private LocalTime startTime;
    private LocalTime endTime;
    private double amount;
}
