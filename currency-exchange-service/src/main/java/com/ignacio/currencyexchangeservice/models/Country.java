package com.in28minutes.microservices.currencyexchangeservice.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
public class Country extends CurrencyIssuer {

    private String name;

    private BigDecimal population;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Language> languages;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<RegionalInternationalUnit> treaties;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Currency> officialCurrencies;


    @OneToMany
    private Set<Language> officialLanguages;

    @Column(name = "surface_in_squared_km")
    private BigDecimal surfaceInSquaredKm;

    public Country() {
    }

    public Country(String name, BigDecimal population, Set<Language> languages,
                   Set<RegionalInternationalUnit> treaties, Set<Currency> officialCurrencies, Set<Language> officialLanguages, BigDecimal surfaceInSquaredKm, Currency officialIssuedCurrency) {
        super(officialIssuedCurrency);
        this.name = name;
        this.population = population;
        this.languages = languages;
        this.treaties = treaties;
        this.officialCurrencies = officialCurrencies;
        this.officialLanguages = officialLanguages;
        this.surfaceInSquaredKm = surfaceInSquaredKm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPopulation() {
        return population;
    }

    public void setPopulation(BigDecimal population) {
        this.population = population;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Set<RegionalInternationalUnit> getTreaties() {
        return treaties;
    }

    public void setTreaties(Set<RegionalInternationalUnit> treaties) {
        this.treaties = treaties;
    }

    public Set<Currency> getOfficialCurrencies() {
        return officialCurrencies;
    }

    public void setOfficialCurrencies(Set<Currency> officialCurrencies) {
        this.officialCurrencies = officialCurrencies;
    }

    public Set<Language> getOfficialLanguages() {
        return officialLanguages;
    }

    public void setOfficialLanguages(Set<Language> officialLanguages) {
        this.officialLanguages = officialLanguages;
    }

    public BigDecimal getSurfaceInSquaredKm() {
        return surfaceInSquaredKm;
    }

    public void setSurfaceInSquaredKm(BigDecimal surfaceInSquaredKm) {
        this.surfaceInSquaredKm = surfaceInSquaredKm;
    }

    /*@Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", population=" + population +
                ", languages=" + languages +
                ", treaties=" + treaties +
                ", officialCurrency=" + officialIssuedCurrency +
                ", officialLanguages=" + officialLanguages +
                ", surfaceInSquaredKm=" + surfaceInSquaredKm +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(name, country.name) &&
                Objects.equals(population, country.population) &&
                Objects.equals(languages, country.languages) &&
                Objects.equals(treaties, country.treaties) &&
                Objects.equals(officialIssuedCurrency, country.officialIssuedCurrency) &&
                Objects.equals(officialLanguages, country.officialLanguages) &&
                Objects.equals(surfaceInSquaredKm, country.surfaceInSquaredKm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, population, languages, treaties, officialIssuedCurrency,officialLanguages, surfaceInSquaredKm);
    }*/
}
