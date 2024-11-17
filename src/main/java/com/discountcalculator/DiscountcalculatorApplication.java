package com.discountcalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DiscountcalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscountcalculatorApplication.class, args);
	}

}
