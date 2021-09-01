package com.ignacio.currencyexchangeservice.controllers.currencyexchange;

import com.ignacio.currencyexchangeservice.models.CurrencyExchange;
import com.ignacio.currencyexchangeservice.services.currencyexchange.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Ignacio Galvez
 */
@RestController("/currency-exchange")
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeService service;

    /**
     *
     * @return the list af all existing CurrencyExchanges
     */
    @GetMapping("currency-exchange/all")
    public ResponseEntity<List<CurrencyExchange>> retrieveExchangeCurrencyValue()
    {
        return service.findAll();
    }

    /**
     *
     * @param from the currency symbol which is converted from
     * @param to the currency symbol of the currey to which is converted
     * @return
     */

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ResponseEntity<CurrencyExchange> retrieveExchangeCurrencyValue(@PathVariable String from,
                                                          @PathVariable String to)
    {
        return service.findCurrencyExchange(from,to);
    }

    /**
     * Creates a nes CurrencyExchange
     * @param currencyExchange the json data for creating th CurrencyExchange
     * @return
     */
    @PostMapping("/currency-exchange")
    public ResponseEntity<CurrencyExchange> createCurrencyExchange(@RequestBody @Valid CurrencyExchange currencyExchange) {
        return service.createCurrencyExchange(currencyExchange);
    }

    /**
     *
     * @param id of the CurrencyExchange to be updated
     * @param conversionMultiple the new conversion multiple
     * @return accepted if the request succeds
     */
    @PutMapping("/currency-exchange/id/{id}/conversionMultiple/{conversionMultiple}")
    public ResponseEntity<CurrencyExchange> updateCurrencyExchange(@PathVariable Long id,
                                                                   @PathVariable BigDecimal conversionMultiple) {
        return service.updateCurrencyExchange(id,conversionMultiple);
    }

    /**
     * Updates an existing CurrencyExchange
     * @param from the currency symbol from which convert to
     * @param to the currency symbol to convert
     * @param conversionMultiple
     * @return
     */
    @PutMapping("/currency-exchange/from/{from}/to/{to}/conversionMultiple/{conversionMultiple}")
    public ResponseEntity<CurrencyExchange> updateCurrencyExchange(@PathVariable String from,
                                                                   @PathVariable String to,
                                                                   @PathVariable BigDecimal conversionMultiple) {
        return service.updateCurrencyExchange(from,to,conversionMultiple);
    }

    @DeleteMapping("/currency-exchange/id/{id}")
    public void deleteCurrencyExchange(@PathVariable Long id) {
        service.deleteCurrencyExchange(id);
    }
}
