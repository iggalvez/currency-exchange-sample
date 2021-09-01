package com.ignacio.currencyexchangeservice.services.currencyexchange;

import com.ignacio.currencyexchangeservice.models.CurrencyExchange;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RequestValidatorService {

    public void validateCurrencyExchange(CurrencyExchange currencyExchange) {
        if (currencyExchange == null || !validateConversionMultiple(currencyExchange)) {
            throw new RuntimeException("CurrencyExchange json malformed");
        }
    }

    private boolean validateConversionMultiple(CurrencyExchange currencyExchange) {
        return currencyExchange.getConversionMultiple().doubleValue() > 0;
    }

    public boolean validateRequestConversionCreation(BigDecimal conversionMultiple,
                                                     CurrencyExchange currencyExchange) {
        return (conversionMultiple.doubleValue() > 0) && (currencyExchange == null);
    }

    public boolean currencyExchangeExists(CurrencyExchange currencyExchange) {
        return currencyExchange != null;
    }

    public boolean validateConversionMultiple(BigDecimal conversionMultiple) {
        return conversionMultiple.doubleValue() > 0;
    }
}

