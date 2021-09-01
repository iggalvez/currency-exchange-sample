package com.in28minutes.microservices.currencyexchangeservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurrencyExchangeNotFoundException extends RuntimeException {
    public CurrencyExchangeNotFoundException(String message) {
        super(message);
    }
}
