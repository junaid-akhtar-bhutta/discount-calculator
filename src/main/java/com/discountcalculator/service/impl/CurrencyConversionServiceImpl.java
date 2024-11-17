package com.discountcalculator.service.impl;

import com.discountcalculator.integration.fx.ForeignExchangeService;
import com.discountcalculator.integration.fx.FxRate;
import com.discountcalculator.service.CurrencyConversionService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CurrencyConversionServiceImpl implements CurrencyConversionService {


    private final ForeignExchangeService foreignExchangeService;

    @Override
    public BigDecimal currencyConversion(BigDecimal amount, String baseCurrency, String targetCurrency) {

        FxRate fxRate = foreignExchangeService.getFxRatesByBaseCurrency(baseCurrency);
        BigDecimal conversionRate = fxRate.getConversionRates().get(targetCurrency);
        if (conversionRate == null) {
            throw new IllegalStateException("Currency conversion not available in target currency");
        }
        return amount.multiply(conversionRate);
    }
}
