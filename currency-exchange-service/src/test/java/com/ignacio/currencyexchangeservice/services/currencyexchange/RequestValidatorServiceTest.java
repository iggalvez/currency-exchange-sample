package com.ignacio.currencyexchangeservice.services.currencyexchange;

import com.ignacio.currencyexchangeservice.models.CurrencyExchange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RequestValidatorServiceTest {
    private final RequestValidatorService validatorService = new RequestValidatorService();
    private final CurrencyExchange currencyExchange = new CurrencyExchange("","", BigDecimal.TEN);
    private final CurrencyExchange zeroCurrencyExchange = new CurrencyExchange("","",BigDecimal.ZERO);
    private final CurrencyExchange negativeCurrencyExchange = new CurrencyExchange("","",BigDecimal.valueOf(-10));

    @Test
    public void currencyExchangeExistsTest(){
        assertFalse(validatorService.currencyExchangeExists(null));
        assertTrue(validatorService.currencyExchangeExists(currencyExchange));
        assertTrue(validatorService.currencyExchangeExists(zeroCurrencyExchange));
        assertTrue(validatorService.currencyExchangeExists(negativeCurrencyExchange));
    }

    @Test
    public void validateConversionMultipleTest(){
        assertFalse(validatorService.validateConversionMultiple(zeroCurrencyExchange.getConversionMultiple()));
        assertFalse(validatorService.validateConversionMultiple(negativeCurrencyExchange.getConversionMultiple()));
        assertTrue(validatorService.validateConversionMultiple(currencyExchange.getConversionMultiple()));
    }

    @Test
    public void validateCurrencyExchangeTest() {
        assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                validatorService.validateCurrencyExchange(zeroCurrencyExchange);
            }
        });

        assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                validatorService.validateCurrencyExchange(negativeCurrencyExchange);
            }
        });
        assertThrows(RuntimeException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                validatorService.validateCurrencyExchange(null);
            }
        });
    }
    @Test
    public void validateRequestConversionCreationTest() {
        assertTrue(validatorService.
                validateRequestConversionCreation(BigDecimal.valueOf(10),
                null));
        assertFalse(validatorService.
                validateRequestConversionCreation(BigDecimal.ZERO,
                currencyExchange));
        assertFalse(validatorService.
                validateRequestConversionCreation(BigDecimal.valueOf(-231),
                currencyExchange));
        assertFalse(validatorService.
                validateRequestConversionCreation(BigDecimal.TEN,
                currencyExchange));
    }
}
