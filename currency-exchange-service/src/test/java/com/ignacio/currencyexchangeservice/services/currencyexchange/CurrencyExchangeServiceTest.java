package com.ignacio.currencyexchangeservice.services.currencyexchange;

import com.ignacio.currencyexchangeservice.models.CurrencyExchange;
import com.ignacio.currencyexchangeservice.persistence.repository.IRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
public class CurrencyExchangeServiceTest {
    @Mock
    IRepository<CurrencyExchange,Long> repositoryMock;

    @Mock
    Environment environment;

    @Mock
    private CurrencyExchangeRequestService requestService;

    @InjectMocks
    private CurrencyExchangeService currencyExchangeService;

    private CurrencyExchange usdToInrCurrencyExchange = new CurrencyExchange("USD","INR",BigDecimal.valueOf(65));
    private CurrencyExchange eurToInrCurrencyExchange = new CurrencyExchange("EUR", "INR",BigDecimal.valueOf(75));
    private CurrencyExchange  audToInrCurrencyExchange = new CurrencyExchange("AUD","INR",BigDecimal.valueOf(25));
    private CurrencyExchange  arsToUsdCurrencyExchange = new CurrencyExchange("USD","ARS",BigDecimal.valueOf(200));
    private List<CurrencyExchange> currencyExchangeList = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        createExchanges();
        createExchangeList();
    }

    private void createExchangeList() {
        currencyExchangeList.add(usdToInrCurrencyExchange);
        currencyExchangeList.add(eurToInrCurrencyExchange);
        currencyExchangeList.add(audToInrCurrencyExchange);
    }

    private void createExchanges() {
        usdToInrCurrencyExchange.setEnvironment("");
        usdToInrCurrencyExchange.setId(100L);
        eurToInrCurrencyExchange.setEnvironment("");
        eurToInrCurrencyExchange.setId(101L);
        audToInrCurrencyExchange.setEnvironment("");
        audToInrCurrencyExchange.setId(102L);
    }

    @Test
    public void retrievingAllCurrencyExchangesTest() {

        when(repositoryMock.findAll())
                .thenReturn(currencyExchangeList);
        when(environment.
                        getProperty(CurrencyExchangeService
                                .LOCAL_SERVER_PORT)).
                thenReturn("8001");
        when(requestService.findAllResponse(currencyExchangeList)).thenReturn(
                new ResponseEntity<>(currencyExchangeList,HttpStatus.OK)
        );
        assertEquals(3, currencyExchangeService.findAll().getBody().size());
        for( CurrencyExchange currencyExchange: currencyExchangeService.findAll().getBody()) {
            assertEquals("8001",currencyExchange.
                    getEnvironment());
        }
        assertEquals(usdToInrCurrencyExchange, currencyExchangeService.findAll().getBody().get(0));
        assertEquals(eurToInrCurrencyExchange, currencyExchangeService.findAll().getBody().get(1));
        assertEquals(audToInrCurrencyExchange, currencyExchangeService.findAll().getBody().get(2));
    }

    @Test
    public void retrieveCurrencyExchangeStatusReturnsOkTest() throws Exception {
        String from = "USD";
        String to ="INR";
        when(environment.
                        getProperty(CurrencyExchangeService
                                .LOCAL_SERVER_PORT)).
                thenReturn("8001");
        when(repositoryMock.find(from,to))
                .thenReturn(Optional.of(usdToInrCurrencyExchange));
        when(requestService.findOk(usdToInrCurrencyExchange))
                .thenReturn(ResponseEntity.ok()
                        .body(usdToInrCurrencyExchange));
        assertEquals("8001",
                                    currencyExchangeService.
                                            findCurrencyExchange(from,to)
                                            .getBody()
                                            .getEnvironment());
        assertEquals(100L, currencyExchangeService.
                findCurrencyExchange(from,to).getBody().getId());
        }


    @Test
    public void createANewCurrencyExchangeShouldReturnCreated() {
        mockCurrencyExchangeRetrieve(arsToUsdCurrencyExchange, null);
        requestServiceExpectedBehavior(arsToUsdCurrencyExchange);
        ResponseEntity<CurrencyExchange> response = currencyExchangeService.
                createCurrencyExchange(arsToUsdCurrencyExchange);
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
    }

    @Test
        public void createAnExistingCurrencyExchangeShouldReturnBadRequest() {
        mockCurrencyExchangeRetrieve(arsToUsdCurrencyExchange, arsToUsdCurrencyExchange);
        requestServiceExpectedBehavior(arsToUsdCurrencyExchange);
            ResponseEntity<CurrencyExchange> response = currencyExchangeService.
                    createCurrencyExchange(arsToUsdCurrencyExchange);
            assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        }

    private void requestServiceExpectedBehavior(CurrencyExchange currencyExchange) {
        when(requestService.makeCurrencyExchangeCreatedResponse(currencyExchange,
                        false,
                        currencyExchange.getConversionMultiple())).
                thenReturn(new ResponseEntity<>(HttpStatus.CREATED));
        when(requestService.makeCurrencyExchangeCreatedResponse(currencyExchange,
                        true,
                        currencyExchange.getConversionMultiple())).
                thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    private void mockCurrencyExchangeRetrieve(CurrencyExchange newCurrencyExchange,
                                              CurrencyExchange currentCurrencyExchange) {
        when(repositoryMock.find(newCurrencyExchange.getFrom(),
                        newCurrencyExchange.getTo()))
                .thenReturn(currentCurrencyExchange == null ? Optional.empty():
                        Optional.of(currentCurrencyExchange));
    }
}

