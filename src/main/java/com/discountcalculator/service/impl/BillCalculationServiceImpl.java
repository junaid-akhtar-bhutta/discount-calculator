package com.discountcalculator.service.impl;

import com.discountcalculator.model.BillCalculationRequest;
import com.discountcalculator.model.BillCalculationResponse;
import com.discountcalculator.model.Item;
import com.discountcalculator.service.BillCalculationService;
import com.discountcalculator.service.CurrencyConversionService;
import com.discountcalculator.service.DiscountCalculationService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BillCalculationServiceImpl implements BillCalculationService {


    private final DiscountCalculationService discountCalculationService;
    private final CurrencyConversionService currencyConversionService;

    @Override
    public BillCalculationResponse calculate(BillCalculationRequest calculationRequest) {

        BigDecimal totalBillAmount = getTotalBillTotal(calculationRequest);
        BigDecimal netDiscountedAmount = discountCalculationService.getNetDiscountedAmount(
                calculationRequest,
                totalBillAmount);
        BigDecimal billTotalAfterCcyConversion = getBillTotalAfterCcyConversion(calculationRequest, totalBillAmount);
        BigDecimal discountedAmountAfterConversion = getDiscountedAmountAfterConversion(
                calculationRequest,
                netDiscountedAmount);

        return new BillCalculationResponse(
                billTotalAfterCcyConversion.subtract(discountedAmountAfterConversion),
                billTotalAfterCcyConversion);
    }

    private BigDecimal getBillTotalAfterCcyConversion(
            BillCalculationRequest calculationRequest,
            BigDecimal totalBillTotal) {

        return currencyConversionService.currencyConversion(
                totalBillTotal,
                calculationRequest.getOriginalCurrency(),
                calculationRequest.getTargetCurrency());
    }

    private static BigDecimal getTotalBillTotal(BillCalculationRequest calculationRequest) {
        BigDecimal totalBillTotal = calculationRequest.getItems().stream()
                .map(Item::getPrice).reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add);
        return totalBillTotal;
    }

    private BigDecimal getDiscountedAmountAfterConversion(
            BillCalculationRequest calculationRequest,
            BigDecimal discountedAmount) {

        return currencyConversionService.currencyConversion(
                discountedAmount,
                calculationRequest.getOriginalCurrency(),
                calculationRequest.getTargetCurrency());
    }
}


