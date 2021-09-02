package com.ignacio.currencyexchangeservice.services.currencyexchange;

import com.ignacio.currencyexchangeservice.exceptions.CurrencyExchangeNotFoundException;
import com.ignacio.currencyexchangeservice.persistence.repository.IRepository;
import com.ignacio.currencyexchangeservice.models.CurrencyExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author Ignacio Galvez
 * Service That communicates the Database with
 * the CurrencyExchangeController
 */


@Component
public class CurrencyExchangeService {

    public static final String LOCAL_SERVER_PORT = "local.server.port";

    @Autowired
    private IRepository<CurrencyExchange,Long> repository;

    @Autowired
    private CurrencyExchangeRequestService requestService;


    @Autowired
    private Environment environment;

    /**
     *
     * @param from the symbol of the currency to convert from
     * @param to the symbol of the currency to convert to
     * @return The response with the CurrencyExchange searched or not found
     */
    public ResponseEntity<CurrencyExchange> findCurrencyExchange(String from, String to){
        CurrencyExchange currencyExchange = getCurrencyExchange(from, to);
        setPortEnvironment(currencyExchange);
        return requestService.findOk(currencyExchange);
    }
    /**
     *
     * @return All the CurrencyExchanges present in the Application
     */
    public ResponseEntity<List<CurrencyExchange>> findAll() {
        List<CurrencyExchange> currencyExchangeList = repository.findAll();
        setEnvironmentPortToAll(currencyExchangeList);
        ResponseEntity<List<CurrencyExchange>> listResponseEntity = requestService.findAllResponse(currencyExchangeList);
        return listResponseEntity;
    }

    /**
     * deletes the currencyExchange with the id specified
     * Throws a CurrencyExchangeNotFoundException if the object is not
     * in the database
     * @param id the id of the CurrencyExchange to delete from the database
     */
    public void deleteCurrencyExchange(Long id) {
        Optional<CurrencyExchange> currencyExchange = repository.findById(id);
        if(!currencyExchange.isPresent()) {
            throw new CurrencyExchangeNotFoundException("id-" + id);
        }
        repository.delete(currencyExchange.get());
    }

    /**
     * saves a created currencyExchange to the database
     * @param currencyExchange
     * @return The Response of the creation operation
     */
    public ResponseEntity<CurrencyExchange> createCurrencyExchange(CurrencyExchange currencyExchange) throws RuntimeException {

        validateCurrencyExchangeInput(currencyExchange);

        Boolean isAlreadyCreated = repository.find(currencyExchange.getFrom(),
                currencyExchange.getTo()).isPresent();
        ResponseEntity<CurrencyExchange> response =
                requestCurrencyExchange(currencyExchange,
                isAlreadyCreated);
        save(currencyExchange, response);
        return response;
    }

    private void validateCurrencyExchangeInput(CurrencyExchange currencyExchange) {
        if (currencyExchange == null|| currencyExchange.getConversionMultiple().doubleValue() < 0){
            throw new RuntimeException("CurrencyExchange json malformed");
        }
    }

    /**
     * updates an existing  currencyExchange
     * @param id the id of the CurrencyExchange
     * @param conversionMultiple the new conversionMultiple
     * @return The Response of the creation operation
     */
    public ResponseEntity<CurrencyExchange> updateCurrencyExchange(Long id,BigDecimal conversionMultiple) {
        CurrencyExchange currencyExchange = repository.findById(id).orElse(null);
        ResponseEntity<CurrencyExchange> response = validateAndUpdate(conversionMultiple,Optional.of(currencyExchange));
        return response;
    }

    /**
     * updates an existing  currencyExchange
     * @param from the from of the CurrencyExchange
     * @param to the to of CurrencyExchange
     * @param conversionMultiple the new conversionMultiple
     * @return The Response of the update operation
     */
    public ResponseEntity<CurrencyExchange> updateCurrencyExchange(String from, String to,
                                                                   BigDecimal conversionMultiple) {
        Optional<CurrencyExchange> currencyExchange = repository.find(from,to);
        ResponseEntity<CurrencyExchange> response = validateAndUpdate(conversionMultiple, currencyExchange);
        return response;
    }

    /**
     *
     * @param currencyExchange the new CurrencyExchange to be created
     * @param response
     */
    private void save(CurrencyExchange currencyExchange, ResponseEntity<CurrencyExchange> response) {
        if (response.getStatusCode() == HttpStatus.CREATED){
            repository.save(currencyExchange);
        }
    }

    /**
     *
     * @param currencyExchange the currency exchange to be created
     * @param isAlreadyCreated the existing currencyExchange with the same values
     *                                is null if it doesn't exist
     *
     * @return The Response of the created CurrencyExchange
     */
    private ResponseEntity<CurrencyExchange> requestCurrencyExchange(CurrencyExchange currencyExchange, Boolean isAlreadyCreated) {
        ResponseEntity<CurrencyExchange> response = requestService.
                makeCurrencyExchangeCreatedResponse(currencyExchange,
                        isAlreadyCreated, currencyExchange.getConversionMultiple());
        return response;
    }

    /**
     *
     * @param conversionMultiple the new Conversion Multiple
     * @param currencyExchange the currencyExchange to update
     * @return
     */
    private ResponseEntity<CurrencyExchange> validateAndUpdate(BigDecimal conversionMultiple, Optional<CurrencyExchange> currencyExchange) {
        ResponseEntity<CurrencyExchange> response = requestService.updateResponse(currencyExchange.isPresent(), conversionMultiple);
        if (response.getStatusCode() == HttpStatus.ACCEPTED) {
            doUpdate(conversionMultiple,currencyExchange.get());
        }
        return response;
    }

    /**
     * final call to the repository for saving the updated
     * CurrencyExchange it is assumed that the update was successful
     * @param conversionMultiple the new conversionMultiple
     * @param currencyExchange the currencyExchange to modify
     */
    private void doUpdate(BigDecimal conversionMultiple,
                                                      CurrencyExchange currencyExchange) {
        currencyExchange.setConversionMultiple(conversionMultiple);
        repository.save(currencyExchange);
    }

    private void setPortEnvironment(CurrencyExchange currencyExchange) {
        String port = environment.getProperty(LOCAL_SERVER_PORT);
        currencyExchange.setEnvironment(port);
    }

    /**
     *
      * @param from
     * @param to
     * @return the CurrencyExchange from the database that matches with to and from
     */
    private CurrencyExchange getCurrencyExchange(String from, String to) {
        Optional<CurrencyExchange> currencyExchange = repository.find(from, to);
        if (!currencyExchange.isPresent()) {
            throw new RuntimeException("currencyExchange from " + from + " to  "+ to + " not found" );
        }
        return currencyExchange.get();
    }

    private void setEnvironmentPortToAll( List<CurrencyExchange> currencyExchangeList) {
        for (CurrencyExchange currencyExchange: currencyExchangeList){
            setPortEnvironment(currencyExchange);
        }
    }
}
