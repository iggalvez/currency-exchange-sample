package com.ignacio.currencyexchangeservice.persistence.custom;

import com.ignacio.currencyexchangeservice.models.CurrencyExchange;
import com.ignacio.currencyexchangeservice.persistence.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Repository that uses javax.persistence.EntityManager
 */

@Component
@Primary
public class CustomCurrencyExchangeRepository implements IRepository<CurrencyExchange, Long> {

    @Autowired
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    /**
     * @param args must consist on two Strings the first one is from
     *             and the second is the to
     */
    public Optional<CurrencyExchange> find(String... args) {
        String queryString =
                "from CurrencyExchange where currency_from="
                        + "\'" + args[0] + "\' and " + "currency_to=" + "\'" + args[1] + "\'";
        try {
            CurrencyExchange result = (CurrencyExchange) entityManager.createQuery(queryString).getSingleResult();
            return Optional.of(result);
        } catch (NoResultException nre) {
            return null;
        }
    }

    @Override
    @Transactional
    public Optional<CurrencyExchange> findById(Long id) {
        Map<String, Long> map = new HashMap<>();
        CurrencyExchange result = entityManager
                .find(CurrencyExchange.class, id);
        return Optional.of(result);
    }

    @Override
    @Transactional
    public List<CurrencyExchange> findAll() {
        String queryString = "from CurrencyExchange";
        Query query = entityManager.createQuery(queryString);
        return (List<CurrencyExchange>) query.getResultList();
    }

    @Override
    @Transactional
    public void delete(CurrencyExchange currencyExchange) {
        entityManager.remove(currencyExchange);
    }

    @Override
    @Transactional
    public void save(CurrencyExchange currencyExchange) {
        entityManager.persist(currencyExchange);
    }
}
