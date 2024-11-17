package com.discountcalculator.service;

import com.discountcalculator.model.BillCalculationRequest;
import com.discountcalculator.model.BillCalculationResponse;
import java.math.BigDecimal;

public interface DiscountCalculationService {


    BigDecimal getNetDiscountedAmount(BillCalculationRequest calculationRequest, BigDecimal totalBillAmount);
}
