package com.in28minutes.microservices.currencyexchangeservice.persistence.repositories;

import com.in28minutes.microservices.currencyexchangeservice.models.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContinentRepository extends JpaRepository<Continent,Long> {
}
