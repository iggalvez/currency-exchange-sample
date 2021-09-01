package com.ignacio.currencyexchangeservice.persistence.jpa;

import com.ignacio.currencyexchangeservice.models.CurrencyExchange;
import com.ignacio.currencyexchangeservice.persistence.repositories.CurrencyExchangeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@SpringBootTest
public class JpaCurrencyExchangeRepositoryTest {
    private CurrencyExchange usdToInrCurrencyExchange = new CurrencyExchange("USD","INR", BigDecimal.valueOf(65));
    private CurrencyExchange eurToInrCurrencyExchange = new CurrencyExchange("EUR", "INR",BigDecimal.valueOf(75));
    private CurrencyExchange audToInrCurrencyExchange = new CurrencyExchange("AUD", "INR",BigDecimal.valueOf(25));
    private CurrencyExchange arsToUsdCurrencyExchange = new CurrencyExchange("USD","ARS",BigDecimal.valueOf(200));
    private List<CurrencyExchange> currencies = Arrays.asList(usdToInrCurrencyExchange,
            eurToInrCurrencyExchange,
            audToInrCurrencyExchange);
    @Mock
    CurrencyExchangeRepository repository;

    @InjectMocks
    JpaCurrencyExchangeRepository jpaCurrencyExchangeRepository;




    @BeforeEach
    public void setUp() {
        usdToInrCurrencyExchange.setEnvironment("");
        usdToInrCurrencyExchange.setId(100L);

        eurToInrCurrencyExchange.setEnvironment("");
        eurToInrCurrencyExchange.setId(101L);

        audToInrCurrencyExchange.setEnvironment("");
        audToInrCurrencyExchange.setId(102L);

    }

    @Test
    public void findAllMustRetrievesAllData() {
        when(repository.findAll()).thenReturn(currencies);

        assertEquals(3,jpaCurrencyExchangeRepository.findAll().size());
        assertEquals(currencies,jpaCurrencyExchangeRepository.findAll());
    }

    @Test
    public void findWithFromAndToArgsShouldReturnCurrencyExchange() {
        String from = "USD";
        String to = "INR";
        when(repository.findByFromAndTo(from,to))
                .thenReturn(usdToInrCurrencyExchange);
        Assertions.assertEquals(usdToInrCurrencyExchange,
                jpaCurrencyExchangeRepository
                .find(from,to));
    }

    @Test
    public void findByIdShouldReturnCurrencyExchange() {
        Optional<CurrencyExchange> optional = Optional.of(usdToInrCurrencyExchange);
        when(repository.findById(usdToInrCurrencyExchange.getId()))
                .thenReturn(optional);
        Assertions.assertEquals(usdToInrCurrencyExchange,jpaCurrencyExchangeRepository
                .findById(usdToInrCurrencyExchange.getId()).get());
    }
}
