package com.oshurpik.service.dao;

import com.oshurpik.entity.ExchangeHistory;
import java.util.List;

public interface ExchangeHistoryDao extends GenericDao<ExchangeHistory, Integer> {
    public List<ExchangeHistory> findByFromCurrencyName(String name);
    public List getNumberOfTransationsForEachFromCurrency();
    public List getFromCurrencyWithMaxTransactionCount();
}