package com.oshurpik.service;

import com.oshurpik.entity.ExchangeHistory;
import com.oshurpik.repository.ExchangeHistoryRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExchangeHistoryService {

    @Autowired
    private ExchangeHistoryRepository exchangeHistoryRepository;
    
    public List<ExchangeHistory> list() {
        return exchangeHistoryRepository.findAll();
    }
    
    public List<ExchangeHistory> findByFromCurrencyName(String currencyName) {
        return exchangeHistoryRepository.findByFromCurrencyName(currencyName);
    }
    
    public List getNumberOfTransationsForEachFromCurrency() {
        return exchangeHistoryRepository.getNumberOfTransationsForEachFromCurrency();
    }
    
    public List getFromCurrencyWithMaxTransactionCount() {
        return exchangeHistoryRepository.getFromCurrencyWithMaxTransactionCount();
    }

}
