package com.discountcalculator.controller;


import com.discountcalculator.model.BillCalculationRequest;
import com.discountcalculator.model.BillCalculationResponse;
import com.discountcalculator.service.BillCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculate")
@RequiredArgsConstructor
public class BillCalculationController {


    private final BillCalculationService billCalculationService;

    @PostMapping
    public ResponseEntity<BillCalculationResponse> calculateBill(
            @RequestBody BillCalculationRequest calculationRequest) {

        return new ResponseEntity<>(
                billCalculationService.calculate(calculationRequest),
                HttpStatus.OK);
    }
}
