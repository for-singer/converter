package com.oshurpik.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="EXCHANGE_RATE", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"FROM_CURRENCY_ID", "TO_CURRENCY_ID"})})
public class ExchangeRate implements Serializable {

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name="FROM_CURRENCY_ID", nullable = false)
    private Currency fromCurrency;
    
    @ManyToOne
    @JoinColumn(name="TO_CURRENCY_ID", nullable = false)
    private Currency toCurrency;
    
    @Column(name="RATE", nullable=false, precision=23, scale=0)
    private Double rate;

    public ExchangeRate() {
    }

    public ExchangeRate(Currency fromCurrency, Currency toCurrency, Double rate) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.rate = rate;
    }    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.fromCurrency);
        hash = 29 * hash + Objects.hashCode(this.toCurrency);
        hash = 29 * hash + Objects.hashCode(this.rate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExchangeRate other = (ExchangeRate) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fromCurrency, other.fromCurrency)) {
            return false;
        }
        if (!Objects.equals(this.toCurrency, other.toCurrency)) {
            return false;
        }
        if (!Objects.equals(this.rate, other.rate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" + "id=" + id + ", fromCurrency=" + fromCurrency + ", toCurrency=" + toCurrency + ", rate=" + rate + '}';
    }        
    
    
}


