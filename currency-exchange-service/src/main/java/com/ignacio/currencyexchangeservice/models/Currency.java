package com.ignacio.currencyexchangeservice.models;

import javax.persistence.*;

/**
 * @author Ignacio Galvez
 */
@Entity
public class Currency {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name ="currency_symbol" ,nullable = false,unique = true)
    private String currencySymbol;

    @Column(nullable = false)
    private String denomination;
    /**
     * The official issuer of the Currency
     */
    @OneToOne(fetch = FetchType.LAZY)
    private CurrencyIssuer currencyIssuer;

    public Currency() {
    }

    public Currency(String currencySymbol, String denomination,
                    CurrencyIssuer currencyIssuer) {
        this.currencySymbol = currencySymbol;
        this.denomination = denomination;
        this.currencyIssuer = currencyIssuer;
    }

    public Long getId() {
        return id;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String name) {
        this.denomination = name;
    }

    public CurrencyIssuer getCurrencyIssuer() {
        return this.currencyIssuer;
    }

    public void setCurrencyIssuer(CurrencyIssuer currencyIssuer) {
        this.currencyIssuer = currencyIssuer;
    }

}