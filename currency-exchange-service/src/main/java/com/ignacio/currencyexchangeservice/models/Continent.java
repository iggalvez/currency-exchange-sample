package com.ignacio.currencyexchangeservice.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

/**
 * @author Ignacio Galvez
 */

@Entity
public class Continent {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String name;

    /**
     * set of international treaties
     * which includes countries of the continent
     */
    @OneToMany(fetch = FetchType.LAZY)
    private Set<RegionalInternationalUnit> continentalTreaties;

    @OneToMany
    private Set<Country> countries;
    /**
     * Total Continent Territorial Surface
     */
    @Column(name = "surface_in_squared_km")
    private BigDecimal surfaceInSquaredKm;

    public Continent() {
    }

    public Continent(String name, Set<RegionalInternationalUnit> continentalTreaties
            , Set<Country> countries, BigDecimal surfaceInSquaredKm) {
        this.name = name;
        this.continentalTreaties = continentalTreaties;
        this.countries = countries;
        this.surfaceInSquaredKm = surfaceInSquaredKm;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<RegionalInternationalUnit> getContinentalTreaties() {
        return continentalTreaties;
    }

    public void setContinentalTreaties(Set<RegionalInternationalUnit> continentalTreaties) {
        this.continentalTreaties = continentalTreaties;
    }

    public BigDecimal getSurfaceInSquaredKm() {
        return surfaceInSquaredKm;
    }

    public void setSurfaceInSquaredKm(BigDecimal surfaceInSquaredKm) {
        this.surfaceInSquaredKm = surfaceInSquaredKm;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }
}
