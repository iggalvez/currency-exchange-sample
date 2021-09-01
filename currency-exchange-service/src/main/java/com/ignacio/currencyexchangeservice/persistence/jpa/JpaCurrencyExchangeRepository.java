package com.ignacio.currencyexchangeservice.persistence.jpa;

import com.ignacio.currencyexchangeservice.persistence.repository.IRepository;
import com.ignacio.currencyexchangeservice.models.CurrencyExchange;
import com.ignacio.currencyexchangeservice.persistence.repositories.CurrencyExchangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

//@Primary
@Component
public class JpaCurrencyExchangeRepository implements IRepository<CurrencyExchange,Long> {

    @Autowired
    private CurrencyExchangeRepository repository;

    @Override
    public CurrencyExchange find(String... args) {
        return repository.findByFromAndTo(args[0],
                args[1]);
    }

    @Override
    public Optional<CurrencyExchange> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<CurrencyExchange> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(CurrencyExchange currencyExchange) {

    }

    @Override
    public void save(CurrencyExchange currencyExchange) {

    }
}
