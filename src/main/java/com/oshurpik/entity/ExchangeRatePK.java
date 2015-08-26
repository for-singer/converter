package com.oshurpik.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ExchangeRatePK implements Serializable {
    @ManyToOne
    @JoinColumn(name="FROM_CURRENCY_ID")
    private Currency fromCurrency;
    
    @ManyToOne
    @JoinColumn(name="TO_CURRENCY_ID")
    private Currency toCurrency;

    public ExchangeRatePK() {}

    public ExchangeRatePK(Currency fromCurrency, Currency toCurrency) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.fromCurrency != null ? this.fromCurrency.hashCode() : 0);
        hash = 41 * hash + (this.toCurrency != null ? this.toCurrency.hashCode() : 0);
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
        final ExchangeRatePK other = (ExchangeRatePK) obj;
        if (this.fromCurrency != other.fromCurrency && (this.fromCurrency == null || !this.fromCurrency.equals(other.fromCurrency))) {
            return false;
        }
        if (this.toCurrency != other.toCurrency && (this.toCurrency == null || !this.toCurrency.equals(other.toCurrency))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExchangeRatePK{" + "fromCurrency=" + fromCurrency + ", toCurrency=" + toCurrency + '}';
    }
    
    
}
