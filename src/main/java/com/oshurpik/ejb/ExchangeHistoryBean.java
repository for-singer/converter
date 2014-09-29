package com.oshurpik.ejb;

import com.oshurpik.entity.ExchangeHistory;
import com.oshurpik.service.dao.ExchangeHistoryDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExchangeHistoryBean {

    @Autowired
    private ExchangeHistoryDao exchangeHistoryDao;
    
    public List<ExchangeHistory> list() {
        return exchangeHistoryDao.list();
    }
    
    public List<ExchangeHistory> findByFromCurrencyName(String currencyName) {
        return exchangeHistoryDao.findByFromCurrencyName(currencyName);
    }
    
    public List getNumberOfTransationsForEachFromCurrency() {
        return exchangeHistoryDao.getNumberOfTransationsForEachFromCurrency();
    }
    
    public List getFromCurrencyWithMaxTransactionCount() {
        return exchangeHistoryDao.getFromCurrencyWithMaxTransactionCount();
    }

}
