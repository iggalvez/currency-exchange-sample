package com.in28minutes.microservices.currencyexchangeservice.services.currencyexchange;

import com.in28minutes.microservices.currencyexchangeservice.models.CurrencyExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;


@Service
public class CurrencyExchangeRequestService {

    private static Logger logger = LoggerFactory.getLogger(CurrencyExchangeRequestService.class);
    @Autowired
    private RequestValidatorService requestValidatorService;

    public ResponseEntity<CurrencyExchange> makeCurrencyExchangeCreatedResponse
            (CurrencyExchange newCurrencyExchange, CurrencyExchange currentCurrencyExchange,
             BigDecimal conversionMultiple) {
        if (!requestValidatorService
                .validateRequestConversionCreation(conversionMultiple,
                currentCurrencyExchange)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return doReturnCurrencyExchangeCreated(newCurrencyExchange);
    }

    public ResponseEntity<CurrencyExchange> updateResponse(CurrencyExchange currencyExchange, BigDecimal conversionMultiple) {
        if (!requestValidatorService.currencyExchangeExists(currencyExchange)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!requestValidatorService.validateConversionMultiple(conversionMultiple)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    private ResponseEntity<CurrencyExchange> doReturnCurrencyExchangeCreated(CurrencyExchange currencyExchange) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(currencyExchange.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    public ResponseEntity<List<CurrencyExchange>> findAllResponse(
            List<CurrencyExchange> currencyExchangeList) {
        return ResponseEntity.ok().body(currencyExchangeList);
    }

    public ResponseEntity<CurrencyExchange> findOk(CurrencyExchange currencyExchange) {
        return ResponseEntity.ok().body(currencyExchange);
    }
}

