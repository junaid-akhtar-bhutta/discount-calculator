package com.discountcalculator.model;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
public class Item {
    private String name;
    private ItemCategory category;
    private BigDecimal price;
}
