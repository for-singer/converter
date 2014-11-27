package com.oshurpik.service;

import com.oshurpik.entity.Currency;
import com.oshurpik.entity.ExchangeHistory;
import com.oshurpik.repository.CurrencyRepository;
import com.oshurpik.repository.ExchangeHistoryRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ConverterService {

    @Autowired
    private RateService rateService;
    
    @Autowired
    CurrencyRepository currencyRepository;
    
    @Autowired
    ExchangeHistoryRepository exchangeHistoryRepository;
    
    public String convert(String fromCurrency, String toCurrency, Double amount) throws IOException {
        if (fromCurrency.equals(toCurrency)) {
            return String.valueOf(amount);
        }
        if (rateService.getRate(fromCurrency, toCurrency) != null) {
            
            Double result = Math.round(rateService.getRate(fromCurrency, toCurrency) * amount*100.0)/100.0;
            
            Currency toCurrencyObj = currencyRepository.findByName(toCurrency);
            Currency fromCurrencyObj = currencyRepository.findByName(fromCurrency);
            
            ExchangeHistory exchangeHistory = new ExchangeHistory(fromCurrencyObj, toCurrencyObj, amount, new Date());
            
            exchangeHistoryRepository.save(exchangeHistory);
            
            return String.valueOf(result);
        }

        return null;
    }
    
    public String validateParams(String amount, String fromCurrency, String toCurrency) {
        List<String> currencies = new ArrayList<String>();

        Iterable<Currency> currenciesList = currencyRepository.findAll();
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
                errorMessage.append("Param amount=").append(amount).append(" is not double. ");
                hasError = true;
            }

            if (currencies.indexOf(fromCurrency) == -1) {
                errorMessage.append("Param from_currency=").append(fromCurrency).append(" value isn't supported. ");
                hasError = true;
            }

            if (currencies.indexOf(toCurrency) == -1) {
                errorMessage.append("Param to_currency=").append(toCurrency).append(" value isn't supported. ");
                hasError = true;
            }

            if (hasError == false && fromCurrency.equals(toCurrency)) {
                errorMessage.append("Params from_currency and to_currency can't have the same value. ");
                hasError = true;
            }
            try {
                Double.parseDouble(amount);
            } catch (NumberFormatException e) {
                errorMessage.append("Param amount=").append(amount).append(" is not double.");
                hasError = true;
            }
        }
        return String.valueOf(errorMessage).length() > 0? String.valueOf(errorMessage) : null;
    }

}
