package com.ignacio.currencyexchangeservice.services.currencyexchange;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RequestValidatorService {

    public boolean validateConversionMultiple(BigDecimal conversionMultiple) {
        return conversionMultiple.doubleValue() > 0;
    }
}

