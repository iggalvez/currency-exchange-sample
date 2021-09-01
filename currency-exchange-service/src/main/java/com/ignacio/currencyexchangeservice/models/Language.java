package com.ignacio.currencyexchangeservice.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Language {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String languageSymbol;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Country> countries;

    public Language() {
    }

    public Language(String name, String languageSymbol, HashSet<Country> countries) {
        this.name = name;
        this.languageSymbol = languageSymbol;
        this.countries = countries;
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

    public String getLanguageSymbol() {
        return languageSymbol;
    }

    public void setLanguageSymbol(String languageSymbol) {
        this.languageSymbol = languageSymbol;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }
}
