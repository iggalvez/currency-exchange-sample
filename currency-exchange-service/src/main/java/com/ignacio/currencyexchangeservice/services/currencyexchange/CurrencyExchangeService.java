package com.in28minutes.microservices.currencyexchangeservice.services.currencyexchange;

import com.in28minutes.microservices.currencyexchangeservice.exceptions.CurrencyExchangeNotFoundException;
import com.in28minutes.microservices.currencyexchangeservice.models.CurrencyExchange;
import com.in28minutes.microservices.currencyexchangeservice.persistence.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class CurrencyExchangeService {

    public static final String LOCAL_SERVER_PORT = "local.server.port";

    @Autowired
    private IRepository<CurrencyExchange,Long> repository;

    @Autowired
    private CurrencyExchangeRequestService requestService;


    @Autowired
    private Environment environment;

    public ResponseEntity<CurrencyExchange> findCurrencyExchange(String from, String to){
        CurrencyExchange currencyExchange = getCurrencyExchange(from, to);
        setPortEnvironment(currencyExchange);
        return requestService.findOk(currencyExchange);
    }

    public ResponseEntity<List<CurrencyExchange>> findAll() {
        List<CurrencyExchange> currencyExchangeList = repository.findAll();
        setEnvironmentPortToAll(currencyExchangeList);
        ResponseEntity<List<CurrencyExchange>> listResponseEntity = requestService.findAllResponse(currencyExchangeList);
        return listResponseEntity;
    }

    public void deleteCurrencyExchange(Long id) {
        Optional<CurrencyExchange> currencyExchange = repository.findById(id);
        if(!currencyExchange.isPresent()) {
            throw new CurrencyExchangeNotFoundException("id-" + id);
        }
        repository.delete(currencyExchange.get());
    }

    public ResponseEntity<CurrencyExchange> createCurrencyExchange(CurrencyExchange currencyExchange) {
        CurrencyExchange currentCurrencyExchange = repository.find(currencyExchange.getFrom(),
                currencyExchange.getTo());
        ResponseEntity<CurrencyExchange> response = requestCurrencyExchange(currencyExchange, currentCurrencyExchange);
        save(currencyExchange, response);
        return response;
    }

    public ResponseEntity<CurrencyExchange> updateCurrencyExchange(Long id,BigDecimal conversionMultiple) {
        CurrencyExchange currencyExchange = repository.findById(id).orElse(null);
        ResponseEntity<CurrencyExchange> response = validateAndUpdate(conversionMultiple, currencyExchange);
        return response;
    }


    public ResponseEntity<CurrencyExchange> updateCurrencyExchange(String from, String to,
                                                                   BigDecimal conversionMultiple) {
        CurrencyExchange currencyExchange = repository.find(from,to);
        ResponseEntity<CurrencyExchange> response = validateAndUpdate(conversionMultiple, currencyExchange);
        return response;
    }

    private void save(CurrencyExchange currencyExchange, ResponseEntity<CurrencyExchange> response) {
        if (response.getStatusCode() == HttpStatus.CREATED){
            repository.save(currencyExchange);
        }
    }


    private ResponseEntity<CurrencyExchange> requestCurrencyExchange(CurrencyExchange currencyExchange, CurrencyExchange currentCurrencyExchange) {
        ResponseEntity<CurrencyExchange> response = requestService.
                makeCurrencyExchangeCreatedResponse(currencyExchange,
                        currentCurrencyExchange, currencyExchange.getConversionMultiple());
        return response;
    }


    private ResponseEntity<CurrencyExchange> validateAndUpdate(BigDecimal conversionMultiple, CurrencyExchange currencyExchange) {
        ResponseEntity<CurrencyExchange> response = requestService.updateResponse(currencyExchange, conversionMultiple);
        if (response.getStatusCode() == HttpStatus.ACCEPTED) {
            doUpdate(conversionMultiple, currencyExchange);
        }
        return response;
    }

    private void doUpdate(BigDecimal conversionMultiple,
                                                      CurrencyExchange currencyExchange) {
        currencyExchange.setConversionMultiple(conversionMultiple);
        repository.save(currencyExchange);
    }

    private void setPortEnvironment(CurrencyExchange currencyExchange) {
        String port = environment.getProperty(LOCAL_SERVER_PORT);
        currencyExchange.setEnvironment(port);
    }

    private CurrencyExchange getCurrencyExchange(String from, String to) {
        CurrencyExchange currencyExchange = repository.find(from, to);
        if (currencyExchange == null) {
            throw new RuntimeException("currencyExchange from " + from + " to  "+ to + " not found" );
        }
        return currencyExchange;
    }

    private void setEnvironmentPortToAll( List<CurrencyExchange> currencyExchangeList) {
        for (CurrencyExchange currencyExchange: currencyExchangeList){
            setPortEnvironment(currencyExchange);
        }
    }
}
