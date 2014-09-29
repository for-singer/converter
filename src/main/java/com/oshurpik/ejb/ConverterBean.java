package com.oshurpik.ejb;

import com.oshurpik.entity.Currency;
import com.oshurpik.entity.ExchangeHistory;
import com.oshurpik.service.dao.CurrencyDao;
import com.oshurpik.service.dao.ExchangeHistoryDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConverterBean {

    @Autowired
    private RateBean rateBean;
    
    @Autowired
    CurrencyDao currencyDao;
    
    @Autowired
    ExchangeHistoryDao exchangeHistoryDao;
    
    public String convert(String fromCurrency, String toCurrency, Double amount) throws IOException {
        if (fromCurrency.equals(toCurrency)) {
            return String.valueOf(amount);
        }
        if (rateBean.getRate(fromCurrency, toCurrency) != null) {
            
            Double result = Math.round(rateBean.getRate(fromCurrency, toCurrency) * amount*100.0)/100.0;
            
            Currency toCurrencyObj = currencyDao.findByName(toCurrency);
            Currency fromCurrencyObj = currencyDao.findByName(fromCurrency);
            
            ExchangeHistory exchangeHistory = new ExchangeHistory(fromCurrencyObj, toCurrencyObj, amount, new Date());
            
            exchangeHistoryDao.add(exchangeHistory);
            
            return String.valueOf(result);
        }

        return null;
    }
    
    public String validateParams(String amount, String fromCurrency, String toCurrency) {
        List<String> currencies = new ArrayList<String>();
        List<Currency> currenciesList = currencyDao.list();
        for (Currency currency : currenciesList) {
            currencies.add(currency.getName());
        }
        boolean hasError = false;
        StringBuilder errorMessage = new StringBuilder();

        if (amount == null) {
            errorMessage.append("Param 'amount' is not set. ");
            hasError = true;
        }
        if (fromCurrency == null) {
            errorMessage.append("Param 'fromCurrency' is not set. ");
            hasError = true;
        }
        if (toCurrency == null) {
            errorMessage.append("Param 'toCurrency' is not set. ");
            hasError = true;
        }

        if (hasError == false) {
            try {
                Double.parseDouble(amount);
            } catch (NumberFormatException e) {
                errorMessage.append("Param amount=" + amount + " is not double. ");
                hasError = true;
            }

            if (currencies.indexOf(fromCurrency) == -1) {
                errorMessage.append("Param from_currency=" + fromCurrency + " value isn't supported. ");
                hasError = true;
            }

            if (currencies.indexOf(toCurrency) == -1) {
                errorMessage.append("Param to_currency=" + toCurrency + " value isn't supported. ");
                hasError = true;
            }

            if (hasError == false && fromCurrency.equals(toCurrency)) {
                errorMessage.append("Params from_currency and to_currency can't have the same value. ");
                hasError = true;
            }
            try {
                Double.parseDouble(amount);
            } catch (NumberFormatException e) {
                errorMessage.append("Param amount=" + amount + " is not double.");
                hasError = true;
            }
        }
        return String.valueOf(errorMessage).length() > 0? String.valueOf(errorMessage) : null;
    }

}
