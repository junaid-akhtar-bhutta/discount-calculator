package com.discountcalculator.service;

import com.discountcalculator.model.BillCalculationRequest;
import com.discountcalculator.model.BillCalculationResponse;

public interface BillCalculationService {
    BillCalculationResponse calculate(BillCalculationRequest calculationRequest);




}
