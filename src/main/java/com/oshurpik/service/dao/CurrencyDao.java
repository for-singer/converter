package com.oshurpik.service.dao;

import com.oshurpik.entity.Currency;

public interface CurrencyDao extends GenericDao<Currency, Integer> {
    public Currency findByName(String name);
}