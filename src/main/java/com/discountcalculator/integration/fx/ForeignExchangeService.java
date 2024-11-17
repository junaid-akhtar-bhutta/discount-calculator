package com.discountcalculator.integration.fx;

import java.math.BigDecimal;
import java.util.List;

public interface ForeignExchangeService {


    FxRate getFxRatesByBaseCurrency(String currency);

}
