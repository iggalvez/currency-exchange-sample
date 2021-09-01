package com.ignacio.currencyexchangeservice.persistence.repositories;

import com.ignacio.currencyexchangeservice.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {
}
