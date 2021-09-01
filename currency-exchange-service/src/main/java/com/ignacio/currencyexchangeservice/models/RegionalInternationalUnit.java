package com.in28minutes.microservices.currencyexchangeservice.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Entity
public class RegionalInternationalUnit extends CurrencyIssuer{

    @Column(unique = true)
    private String name;

    @Column(name = "unit_formal_start",updatable = false)
    private LocalDate unitFormalStart;

    @Column(name= "signing_date",updatable = false)
    private LocalDate signingDate;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @OneToMany
    private Set<Country> countries;

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
