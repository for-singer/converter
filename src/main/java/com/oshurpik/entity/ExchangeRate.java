package com.oshurpik.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="EXCHANGE_RATE")
public class ExchangeRate implements Serializable {

    @EmbeddedId
    public ExchangeRatePK id;
   
    
    @Column(name="RATE", nullable=false, precision=23, scale=0)
    private double rate;

    public ExchangeRate() {
    }  

    public ExchangeRate(ExchangeRatePK id, double rate) {
        this.id = id;
        this.rate = rate;
    }

    public ExchangeRatePK getId() {
        return id;
    }

    public void setId(ExchangeRatePK id) {
        this.id = id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 71 * hash + (int) (Double.doubleToLongBits(this.rate) ^ (Double.doubleToLongBits(this.rate) >>> 32));
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
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (Double.doubleToLongBits(this.rate) != Double.doubleToLongBits(other.rate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" + "id=" + id + ", rate=" + rate + '}';
    }
    
    
}


