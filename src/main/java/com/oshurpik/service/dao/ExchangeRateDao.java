package com.oshurpik.service.dao;

import com.oshurpik.entity.Currency;
import com.oshurpik.entity.ExchangeRate;
import com.oshurpik.entity.ExchangeRatePK;
import java.util.List;

public interface ExchangeRateDao extends GenericDao<ExchangeRate, ExchangeRatePK> {

    List<ExchangeRate> findByFromNameToName(String fromCurrencyName, String toCurrencyName);
}