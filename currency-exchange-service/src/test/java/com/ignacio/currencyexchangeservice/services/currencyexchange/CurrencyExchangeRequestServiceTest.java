package com.in28minutes.microservices.currencyexchangeservice.services.currencyexchange;

import com.in28minutes.microservices.currencyexchangeservice.models.CurrencyExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CurrencyExchangeRequestServiceTest {

    @Mock
    private RequestValidatorService requestValidatorService;

    @InjectMocks
    private CurrencyExchangeRequestService requestService;
    private CurrencyExchange usdToInrCurrencyExchange = new CurrencyExchange("USD","INR",BigDecimal.valueOf(65));
    private CurrencyExchange eurToInrCurrencyExchange = new CurrencyExchange("EUR", "INR",BigDecimal.valueOf(75));
    private CurrencyExchange audToInrCurrencyExchange = new CurrencyExchange("AUD", "INR",BigDecimal.valueOf(25));
    private CurrencyExchange arsToUsdCurrencyExchange = new CurrencyExchange("USD","ARS",BigDecimal.valueOf(200));
    private List<CurrencyExchange> currencies = Arrays.asList(usdToInrCurrencyExchange,
            eurToInrCurrencyExchange,
            audToInrCurrencyExchange);
    @BeforeEach
    private void createExchanges() {

        usdToInrCurrencyExchange.setEnvironment("");
        usdToInrCurrencyExchange.setId(100L);

        eurToInrCurrencyExchange.setEnvironment("");
        eurToInrCurrencyExchange.setId(101L);

        audToInrCurrencyExchange.setEnvironment("");
        audToInrCurrencyExchange.setId(102L);

    }
    
    @Test
    public void findAllAlwaysReturnSuccess(){
        assertEquals(requestService.findAllResponse(null).getStatusCode(),
                HttpStatus.OK);
    }

    @Test
    public void findAllWithDataReturnsResponseWithData() {
        ResponseEntity<List<CurrencyExchange>> response =  requestService.findAllResponse(currencies);
        List<CurrencyExchange> list = response.getBody();
        assertEquals(3,list.size());
    }

    @Test
    public void NonExistingCurrencyExchangeShouldReturnNotFound() {
        BigDecimal conversionMultiple = BigDecimal.TEN;
        Mockito.when(requestValidatorService.currencyExchangeExists(null))
                .thenReturn(false);
        Mockito.when(requestValidatorService.validateConversionMultiple(conversionMultiple)).
                thenReturn(true);
        assertEquals(HttpStatus.NOT_FOUND, requestService
                .updateResponse(null, conversionMultiple).getStatusCode());
    }
    @Test
    public void existingCurrencyExchangeAndWrongMultipleConversionShouldReturnBadRequest() {
        BigDecimal conversionMultiple = BigDecimal.valueOf(-100);
        Mockito.when(requestValidatorService.currencyExchangeExists(arsToUsdCurrencyExchange))
                .thenReturn(true);
        Mockito.when(requestValidatorService.validateConversionMultiple(conversionMultiple))
                .thenReturn(false);

        assertEquals(HttpStatus.BAD_REQUEST, requestService.updateResponse(arsToUsdCurrencyExchange, conversionMultiple).getStatusCode());
    }

    @Test
    public void existingCurrencyExchangeShouldReturnAccepted() {
        BigDecimal conversionMultiple = BigDecimal.TEN;
        Mockito.when(requestValidatorService.currencyExchangeExists(arsToUsdCurrencyExchange))
                .thenReturn(true);
        Mockito.when(requestValidatorService.validateConversionMultiple(conversionMultiple))
                .thenReturn(true);
        assertEquals(HttpStatus.ACCEPTED, requestService.updateResponse(arsToUsdCurrencyExchange, conversionMultiple).getStatusCode());
    }


    @Test
    public void createAnExistingCurrencyExchangeShouldReturnBadRequest() {
        Mockito.when(requestValidatorService.validateRequestConversionCreation(usdToInrCurrencyExchange.getConversionMultiple(),
                usdToInrCurrencyExchange)).thenReturn(false);


        ResponseEntity<CurrencyExchange> response = requestService
                .makeCurrencyExchangeCreatedResponse(usdToInrCurrencyExchange,
                        usdToInrCurrencyExchange,
                usdToInrCurrencyExchange.getConversionMultiple());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    public void findOkReturnsOkWithResponseBody(){
        assertEquals(HttpStatus.OK,requestService.findOk(usdToInrCurrencyExchange)
                .getStatusCode());
        assertEquals(usdToInrCurrencyExchange,requestService.findOk(usdToInrCurrencyExchange).getBody());
    }

}
