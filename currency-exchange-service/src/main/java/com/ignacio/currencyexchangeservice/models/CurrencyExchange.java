package com.ignacio.currencyexchangeservice.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * @author Ignacio Galvez
 */
@Entity
public class CurrencyExchange {
    @Id
    @GeneratedValue
    private Long id;
    /**
     * The currency symbol to convert from
     */
    @Column(name = "currency_from", nullable = false)
    private String from;
    /**
     * The currency symbol to convert to
     */
    @Column(name = "currency_to", nullable = false)
    private String to;
    /**
     * The value of one unit of from currency in to currency
     */
    @Column(name = "conversion_multiple",nullable = false)
    private BigDecimal conversionMultiple;
    /**
     * additional environment info such as the port
     * where the CurrencyExchange Service is running
     */
    private String environment;

    public CurrencyExchange() {

    }

    public CurrencyExchange( String from, String to, BigDecimal conversionMultiple) {
        this.from = from;
        this.to = to;
        this.conversionMultiple = conversionMultiple;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getConversionMultiple() {
        return conversionMultiple;
    }

    public void setConversionMultiple(BigDecimal conversionMultiple) {
        this.conversionMultiple = conversionMultiple;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    @Override
    public String toString() {
        return "CurrencyExchange{" +
                "id=" + id +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", conversionMultiple=" + conversionMultiple +
                ", environment='" + environment + '\'' +
                '}';
    }
}
