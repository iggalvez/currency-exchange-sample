package com.ignacio.currencyexchangeservice.persistence.repositories;

import com.ignacio.currencyexchangeservice.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language,Long> {
}
