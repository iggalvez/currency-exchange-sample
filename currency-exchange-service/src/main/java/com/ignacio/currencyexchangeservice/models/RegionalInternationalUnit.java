package com.ignacio.currencyexchangeservice.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author Ignacio Galvez
 * International Treaty between two or more countries
 * resulting in a higher State Regulator that each individual country
 */
@Entity
public class RegionalInternationalUnit extends CurrencyIssuer{

    @Column(unique = true)
    private String name;
    /**
     * The date where the treaty stars to be legal
      */
    @Column(name = "unit_formal_start",updatable = false)
    private LocalDate unitFormalStart;
    /**
     * The date of the Treaty signing
     */
    @Column(name= "signing_date",updatable = false)
    private LocalDate signingDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    /**
     * Treaty Signing Countries
     */
    @OneToMany
    private Set<Country> countries;
    /**
     * Continents of the signing countries
     */
    @OneToMany
    private Set<Continent> continents;

    public RegionalInternationalUnit() {
    }

    public RegionalInternationalUnit(String name, LocalDate unitFormalStart, LocalDate signingDate,
                                     Set<Country> countries,
                                     Currency officialIssuedCurrency, Set<Continent> continents) {
        super(officialIssuedCurrency);
        this.name = name;
        this.unitFormalStart = unitFormalStart;
        this.signingDate = signingDate;
        this.countries = countries;
        this.continents = continents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getUnitFormalStart() {
        return unitFormalStart;
    }

    public void setUnitFormalStart(LocalDate unitFormalStart) {
        this.unitFormalStart = unitFormalStart;
    }

    public LocalDate getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(LocalDate signingDate) {
        this.signingDate = signingDate;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public Set<Continent> getContinents() {
        return continents;
    }

    public void setContinents(Set<Continent> continents) {
        this.continents = continents;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
}
