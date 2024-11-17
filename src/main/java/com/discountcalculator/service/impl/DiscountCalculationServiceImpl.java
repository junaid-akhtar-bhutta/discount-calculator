package com.discountcalculator.service.impl;

import com.discountcalculator.integration.fx.ForeignExchangeService;
import com.discountcalculator.model.BillCalculationRequest;
import com.discountcalculator.model.Item;
import com.discountcalculator.model.ItemCategory;
import com.discountcalculator.model.UserType;
import com.discountcalculator.service.CurrencyConversionService;
import com.discountcalculator.service.DiscountCalculationService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DiscountCalculationServiceImpl implements DiscountCalculationService {


    public static final int CUSTOMER_DISCOUNT_TENURE_LIMIT = 24;
    private static int FIXED_DISCOUNT_AMT_IN_USD = 5;
    private static int FIXED_DISCOUNT_BILL_AMT_IN_USD = 100;

    private final CurrencyConversionService currencyConversionService;

    @Override
    public BigDecimal getNetDiscountedAmount(BillCalculationRequest calculationRequest, BigDecimal totalBillAmount) {

        BigDecimal percentageDiscount = getPercentageDiscount(calculationRequest);
        BigDecimal fixedDiscount = getFixedDiscount(totalBillAmount, calculationRequest.getOriginalCurrency());

        return percentageDiscount.add(fixedDiscount);
    }


    private BigDecimal getPercentageDiscount(BillCalculationRequest billCalculationRequest) {

        BigDecimal billTotal = getTotalBillExcludingIgnoredCategories(billCalculationRequest);
        UserType userType = billCalculationRequest.getUserType();

        int discountPercentage = switch (userType) {
            case Employee -> 30;
            case Affiliate -> 10;
            case Customer ->
                    billCalculationRequest.getCustomerTenureInMonths() >= CUSTOMER_DISCOUNT_TENURE_LIMIT ? 5 : 0;
            default -> 0;
        };

        return calculatePercentageValue(billTotal, discountPercentage);

    }

    private static BigDecimal calculatePercentageValue(BigDecimal billTotal, int discountPercentage) {
        return billTotal.multiply(
                        BigDecimal.valueOf(discountPercentage))
                .divide(BigDecimal.valueOf(100),
                        RoundingMode.HALF_UP);
    }

    private BigDecimal getFixedDiscount(BigDecimal netAmount, String baseCcy) {

        BigDecimal netAmountInUSD = currencyConversionService.currencyConversion(netAmount, baseCcy, "USD");

        BigDecimal increments = netAmountInUSD.divide(
                BigDecimal.valueOf(FIXED_DISCOUNT_BILL_AMT_IN_USD),
                0,
                RoundingMode.FLOOR);
        BigDecimal discountAmtInUSD = increments.multiply(BigDecimal.valueOf(FIXED_DISCOUNT_AMT_IN_USD));

        return currencyConversionService.currencyConversion(discountAmtInUSD, "USD", baseCcy);
    }

    private BigDecimal getTotalBillExcludingIgnoredCategories(BillCalculationRequest calculationRequest) {

        return calculationRequest.getItems().stream()
                .filter(item -> !ItemCategory.GROCERIES.equals(item.getCategory()))
                .map(Item::getPrice).reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add);
    }
}
