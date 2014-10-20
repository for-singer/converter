package com.oshurpik.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="EXCHANGE_HISTORY")
public class ExchangeHistory implements Serializable {


    private Integer id;
    private Currency fromCurrency;
    private Currency toCurrency;
    private Double amount;
    private Date transDate;

    public ExchangeHistory() {
    }

    public ExchangeHistory(Currency fromCurrency, Currency toCurrency, Double amount, Date date) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.amount = amount;
        this.transDate = date;
    }

    @Id 
    @GeneratedValue
    @Column(name="ID")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="FROM_CURRENCY_ID",referencedColumnName="ID")
    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurrency = fromCurrency;
    }

    @ManyToOne
    @JoinColumn(name="TO_CURRENCY_ID",referencedColumnName="ID")
    public Currency getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurrency = toCurrency;
    }

    @Column(name="AMOUNT")
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    @Override
    public String toString() {
        return "ExchangeHistory{" + "id=" + id + ", fromCurrency=" + fromCurrency + ", toCurrency=" + toCurrency + ", amount=" + amount + ", transDate=" + transDate + '}';
    }
    
    

}


