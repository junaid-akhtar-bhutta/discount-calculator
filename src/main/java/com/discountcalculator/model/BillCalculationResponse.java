package com.discountcalculator.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
@AllArgsConstructor
public class BillCalculationResponse {

    private BigDecimal netPayableAmount;
    private BigDecimal billTotal;
}
