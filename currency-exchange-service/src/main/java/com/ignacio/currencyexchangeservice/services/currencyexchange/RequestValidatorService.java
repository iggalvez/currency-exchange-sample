package com.ignacio.currencyexchangeservice.services.currencyexchange;

import com.ignacio.currencyexchangeservice.models.CurrencyExchange;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RequestValidatorService {

    /**
     * validates that the currencyExchange is not null and has a correct
     * conversionMultiple
     * @param currencyExchange the new CurrencyConversion to be created
     */
    public void validateCurrencyExchange(CurrencyExchange currencyExchange) {
        if (currencyExchange == null || !validateConversionMultiple(currencyExchange)) {
            throw new RuntimeException("CurrencyExchange json malformed");
        }
    }

    private boolean validateConversionMultiple(CurrencyExchange currencyExchange) {
        return currencyExchange.getConversionMultiple().doubleValue() > 0;
    }

    /**
     *
     * @param conversionMultiple must be greater than 0
     * @param currencyExchange if it is null means that the CurrencyExchange to be created
     *                         does not exist in the database
     * @return
     */
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

