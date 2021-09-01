package com.in28minutes.microservices.currencyexchangeservice.services.countries;

import com.in28minutes.microservices.currencyexchangeservice.models.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class CurrencyRequestServiceTest {

    private CurrencyRequestService service;

    private Currency nullCurrency = null;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void nonExistingCurrencyReturnsNotFound() {

    }

}
