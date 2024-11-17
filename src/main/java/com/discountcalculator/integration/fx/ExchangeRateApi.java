package com.discountcalculator.integration.fx;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeRateApi implements ForeignExchangeService {


    @Value("${fx-service.exchange-rate-api.base-url}")
    private String baseUrl;

    @Value("${fx-service.exchange-rate-api.api-key}")
    private String apiKey;

    @Autowired
    private  RestTemplate restTemplate;

    @Override
    @Cacheable("fxRates")
    public FxRate getFxRatesByBaseCurrency(String baseCurrency) {

        ExchangeRateApiResponse apiResponse = restTemplate.getForObject(
                baseUrl + "/" + apiKey + "/latest/" + baseCurrency,
                ExchangeRateApiResponse.class);

        return new FxRate(baseCurrency, apiResponse.getConversionRates());
    }
}
