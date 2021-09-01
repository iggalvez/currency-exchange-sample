package com.in28minutes.microservices.currencyexchangeservice.persistence.custom;

import com.in28minutes.microservices.currencyexchangeservice.models.CurrencyExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CustomCurrencyExchangeRepositoryTest {
    private CurrencyExchange usdToInrCurrencyExchange = new CurrencyExchange("USD","INR", BigDecimal.valueOf(65));
    private CurrencyExchange eurToInrCurrencyExchange = new CurrencyExchange("EUR", "INR",BigDecimal.valueOf(75));
    private CurrencyExchange audToInrCurrencyExchange = new CurrencyExchange("AUD", "INR",BigDecimal.valueOf(25));
    private CurrencyExchange arsToUsdCurrencyExchange = new CurrencyExchange("USD","ARS",BigDecimal.valueOf(200));
    private List<CurrencyExchange> currencies = Arrays.asList(usdToInrCurrencyExchange,
            eurToInrCurrencyExchange,
            audToInrCurrencyExchange);
    private final String queryAll = "SELECT * FROM CURRENCY_EXCHANGE;";


    @Mock
    EntityManager entityManager;

    @InjectMocks
    CustomCurrencyExchangeRepository repository;

    @Mock
    Query query;

    @BeforeEach
    public void  setUp() {
        usdToInrCurrencyExchange.setEnvironment("");
        usdToInrCurrencyExchange.setId(100L);

        eurToInrCurrencyExchange.setEnvironment("");
        eurToInrCurrencyExchange.setId(101L);

        audToInrCurrencyExchange.setEnvironment("");
        audToInrCurrencyExchange.setId(102L);
    }

    @Test
    public void findByToAndFromShouldReturnSuccesfully() {
        String queryString =
                "from CurrencyExchange where currency_from="
                        + "\'" +usdToInrCurrencyExchange.getFrom()
                        + "\' and "+ "currency_to=" + "\'"
                        + usdToInrCurrencyExchange.getTo()
                        + "\'";
        when(entityManager.createQuery(queryString
        )).thenReturn(
                query);
        when(query.getSingleResult()).thenReturn(usdToInrCurrencyExchange);
        assertEquals(usdToInrCurrencyExchange,
                repository.find(usdToInrCurrencyExchange.getFrom(),
                usdToInrCurrencyExchange.getTo()));
    }

    @Test
    public void findByIdShouldReturnSuccessfully() {
        when(entityManager.find(CurrencyExchange.class,usdToInrCurrencyExchange.getId()))
                .thenReturn(usdToInrCurrencyExchange);
        assertEquals(usdToInrCurrencyExchange,
                repository.findById(usdToInrCurrencyExchange.getId()).get());
    }
    @Test
    public void findAllShouldRetrieveAllData(){
        String queryString = "from CurrencyExchange";
        when(entityManager.createQuery(queryAll)).thenReturn(
                query);
        when(entityManager.createQuery(queryString
        )).thenReturn(
                query);
        when(query.getResultList()).thenReturn(currencies);
        assertEquals(currencies,repository.findAll());
    }
}
