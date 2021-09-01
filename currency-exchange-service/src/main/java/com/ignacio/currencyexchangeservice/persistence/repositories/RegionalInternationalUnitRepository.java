package com.ignacio.currencyexchangeservice.persistence.repositories;

import com.ignacio.currencyexchangeservice.models.RegionalInternationalUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionalInternationalUnitRepository extends JpaRepository<RegionalInternationalUnit, Long> {
}
