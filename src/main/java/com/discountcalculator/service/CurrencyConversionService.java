package com.discountcalculator.service;

import java.math.BigDecimal;

public interface CurrencyConversionService {

    BigDecimal currencyConversion(BigDecimal amount, String baseCurrency, String targetCurrency);
}
