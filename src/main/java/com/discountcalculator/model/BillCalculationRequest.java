package com.discountcalculator.model;


import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.User;

@ToString
@Getter
@Setter
public class BillCalculationRequest {

    private List<Item> items;
    private BigDecimal totalAmount;
    private UserType userType;
    private int customerTenureInMonths;
    private String originalCurrency;
    private String targetCurrency;
}
