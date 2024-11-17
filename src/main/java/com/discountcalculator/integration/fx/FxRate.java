package com.discountcalculator.integration.fx;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@AllArgsConstructor
@ToString
public class FxRate {

    private String baseCurrency;
    private Map<String, BigDecimal> conversionRates = new HashMap<>();
}
