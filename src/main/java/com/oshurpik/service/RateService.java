package com.oshurpik.service;

import com.oshurpik.entity.Currency;
import com.oshurpik.entity.ExchangeRate;
import com.oshurpik.entity.ExchangeRatePK;
import com.oshurpik.repository.CurrencyRepository;
import com.oshurpik.repository.ExchangeRateRepository;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RateService {

    @Autowired
    CurrencyRepository currencyRepository;
    
    @Autowired
    ExchangeRateRepository exchangeRateRepository;
    
    public List<ExchangeRate> addData() {
        Currency currency = new Currency("uah");
        Currency currency2 = new Currency("usd");        
        Currency currency3 = new Currency("eur");
        
        currencyRepository.save(currency);
        currencyRepository.save(currency2);
        currencyRepository.save(currency3);
                
        
        ExchangeRate exchangeRate = new ExchangeRate(new ExchangeRatePK(currency2, currency), 12.05);
        ExchangeRate exchangeRate2 = new ExchangeRate(new ExchangeRatePK(currency3, currency), 18.0);
        ExchangeRate exchangeRate3 = new ExchangeRate(new ExchangeRatePK(currency, currency2), 0.0847);
        ExchangeRate exchangeRate4 = new ExchangeRate(new ExchangeRatePK(currency, currency3), 0.0568);
        
        exchangeRateRepository.save(exchangeRate);
        exchangeRateRepository.save(exchangeRate2);
        exchangeRateRepository.save(exchangeRate3);
        exchangeRateRepository.save(exchangeRate4);
        
        
        List<ExchangeRate> list = exchangeRateRepository.findAll();
        
        return list;
    }
       
    public Double getRate(String fromCurrency, String toCurrency) throws IOException {
                 
        try {
            ExchangeRate rate = exchangeRateRepository.findByFromNameToName(fromCurrency, toCurrency).get(0);
            return rate.getRate();
        }
        catch(Exception e) {
            return null;
        }

    }

}
