package com.ignacio.currencyexchangeservice.services.currencyexchange;

import com.ignacio.currencyexchangeservice.models.CurrencyExchange;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RequestValidatorServiceTest {
    private final RequestValidatorService validatorService = new RequestValidatorService();
    private final CurrencyExchange currencyExchange = new CurrencyExchange("","", BigDecimal.TEN);
    private final CurrencyExchange zeroCurrencyExchange = new CurrencyExchange("","",BigDecimal.ZERO);
    private final CurrencyExchange negativeCurrencyExchange = new CurrencyExchange("","",BigDecimal.valueOf(-10));


    @Test
    public void validateConversionMultipleTest(){
        assertFalse(validatorService.validateConversionMultiple(zeroCurrencyExchange.getConversionMultiple()));
        assertFalse(validatorService.validateConversionMultiple(negativeCurrencyExchange.getConversionMultiple()));
        assertTrue(validatorService.validateConversionMultiple(currencyExchange.getConversionMultiple()));
    }
}
