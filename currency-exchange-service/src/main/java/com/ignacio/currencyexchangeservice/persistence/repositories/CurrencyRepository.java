package com.ignacio.currencyexchangeservice.persistence.repositories;

import com.ignacio.currencyexchangeservice.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency,Long> {
}
