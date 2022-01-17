package com.volvo.bean;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class TaxCalculationRequest {

    @NotNull
    private String vehicleType;

    @NotNull
    private List<LocalDateTime> tollDateTimeList;
}
