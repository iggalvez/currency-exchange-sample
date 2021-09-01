package com.ignacio.currencyexchangeservice.models;

import javax.persistence.*;
/**
 * @author ignacio.galvez
 * class representing all National States or blocks of National States
 * that Issue a certain currency
 */
@Entity
public  class CurrencyIssuer {

    @Id
    @GeneratedValue
    private Long id;
    /**
     * currency issued for the present CurrencyIssuer
     */
    @OneToOne(fetch = FetchType.LAZY)
    private Currency officialIssuedCurrency;

    public CurrencyIssuer() {
    }

    public CurrencyIssuer(Currency officialIssuedCurrency) {
        this.officialIssuedCurrency = officialIssuedCurrency;
    }

    public Long getId() {
        return id;
    }

    public Currency getOfficialIssuedCurrency() {
       return officialIssuedCurrency;
    }

    public void setOfficialIssuedCurrency(Currency officialIssuedCurrency) {
        this.officialIssuedCurrency = officialIssuedCurrency;
    }
}
