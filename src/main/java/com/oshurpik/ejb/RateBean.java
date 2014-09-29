package com.oshurpik.ejb;

import com.oshurpik.entity.Currency;
import com.oshurpik.entity.ExchangeRate;
import com.oshurpik.entity.ExchangeRatePK;
import com.oshurpik.service.dao.CurrencyDao;
import com.oshurpik.service.dao.ExchangeRateDao;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RateBean {

    @Autowired
    CurrencyDao currencyDao;
    
    @Autowired
    ExchangeRateDao exchangeRateDao;
    
    public List<ExchangeRate> addData() {
        Currency currency = new Currency("uah");
        Currency currency2 = new Currency("usd");        
        Currency currency3 = new Currency("eur");
        
        currencyDao.add(currency);
        currencyDao.add(currency2);
        currencyDao.add(currency3);
                
        
        ExchangeRate exchangeRate = new ExchangeRate(new ExchangeRatePK(currency2, currency), 12.05);
        ExchangeRate exchangeRate2 = new ExchangeRate(new ExchangeRatePK(currency3, currency), 18.0);
        ExchangeRate exchangeRate3 = new ExchangeRate(new ExchangeRatePK(currency, currency2), 0.0847);
        ExchangeRate exchangeRate4 = new ExchangeRate(new ExchangeRatePK(currency, currency3), 0.0568);
        
        exchangeRateDao.add(exchangeRate);
        exchangeRateDao.add(exchangeRate2);
        exchangeRateDao.add(exchangeRate3);
        exchangeRateDao.add(exchangeRate4);
        
        
        List<ExchangeRate> list = exchangeRateDao.list();
        
        return list;
    }
       
    public Double getRate(String fromCurrency, String toCurrency) throws IOException {
                 
        try {
            ExchangeRate rate = exchangeRateDao.findByFromNameToName(fromCurrency, toCurrency).get(0);
            return rate.getRate();
        }
        catch(Exception e) {
            return null;
        }

    }

}
