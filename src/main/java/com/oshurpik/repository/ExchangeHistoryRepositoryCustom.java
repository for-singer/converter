package com.oshurpik.repository;

import com.oshurpik.entity.ExchangeHistory;
import java.util.List;

public interface ExchangeHistoryRepositoryCustom {
    public List<ExchangeHistory> findByFromCurrencyName(String name);
    public List getNumberOfTransationsForEachFromCurrency();
    public List getFromCurrencyWithMaxTransactionCount();
}