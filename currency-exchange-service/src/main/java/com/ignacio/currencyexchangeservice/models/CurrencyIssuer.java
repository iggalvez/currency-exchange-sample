package com.in28minutes.microservices.currencyexchangeservice.models;

import javax.persistence.*;

@Entity
public  class CurrencyIssuer {

    @Id
    @GeneratedValue
    private Long id;

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
