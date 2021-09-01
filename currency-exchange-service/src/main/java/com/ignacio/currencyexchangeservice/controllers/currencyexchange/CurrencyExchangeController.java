package com.ignacio.currencyexchangeservice.controllers.currencyexchange;

import com.ignacio.currencyexchangeservice.models.CurrencyExchange;
import com.ignacio.currencyexchangeservice.services.currencyexchange.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController("/currency-exchange")
public class CurrencyExchangeController {

    @Autowired
    private CurrencyExchangeService service;

    @GetMapping("currency-exchange/all")
    public ResponseEntity<List<CurrencyExchange>> retrieveExchangeCurrencyValue()
    {
        return service.findAll();
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public ResponseEntity<CurrencyExchange> retrieveExchangeCurrencyValue(@PathVariable String from,
                                                          @PathVariable String to)
    {
        return service.findCurrencyExchange(from,to);
    }

    @PostMapping("/currency-exchange")
    public ResponseEntity<CurrencyExchange> createCurrencyExchange(@RequestBody @Valid CurrencyExchange currencyExchange) {
        return service.createCurrencyExchange(currencyExchange);
    }


    @PutMapping("/currency-exchange/id/{id}/conversionMultiple/{conversionMultiple}")
    public ResponseEntity<CurrencyExchange> updateCurrencyExchange(@PathVariable Long id,
                                                                   @PathVariable BigDecimal conversionMultiple) {
        return service.updateCurrencyExchange(id,conversionMultiple);
    }

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
