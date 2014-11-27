package com.oshurpik.common.controller;

import com.oshurpik.service.RateService;
import com.oshurpik.service.ExchangeHistoryService;
import com.oshurpik.entity.ExchangeHistory;
import com.oshurpik.entity.ExchangeRate;
import com.oshurpik.service.ConverterService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConverterController {
    @Autowired
    private ExchangeHistoryService exchangeHistoryService;
    
    @Autowired
    private RateService rateService;
    
    @Autowired
    private ServletContext servletContext;
    
    @Autowired
    private ConverterService converterService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String convert(@RequestParam(value="from_currency", defaultValue="") String fromCurrency, 
            @RequestParam(value="to_currency", defaultValue="") String toCurrency,
            @RequestParam(value="amount", defaultValue="") String amount,
            ModelMap model) throws IOException {
        
        String errorMessage = converterService.validateParams(amount, fromCurrency, toCurrency);

        String result = new String();
        boolean hasError = false;
        
        if (errorMessage == null) {
            result = converterService.convert(fromCurrency, toCurrency, Double.parseDouble(amount));
        }
        else {
            hasError = true;
            result = String.valueOf(errorMessage);
        }
        
        model.addAttribute("result", result);
        model.addAttribute("hasError", hasError);
        
        return "converter";
    }
    
    @RequestMapping(value = "/exchange-history", method = RequestMethod.GET)
    public String exchangeHistory(@RequestParam(value="from_currency", defaultValue="") String from_currency, ModelMap model) {
        model.addAttribute("from_currency", from_currency);
            
        List<ExchangeHistory>  exchangeHistoryAll = exchangeHistoryService.list();
        List<ExchangeHistory>  exchangeHistoryForFromCurrency = exchangeHistoryService.findByFromCurrencyName(from_currency);
        List exchangeHistoryNumberOfTransationsForEachFromCurrency = exchangeHistoryService.getNumberOfTransationsForEachFromCurrency();
        List exchangeHistoryMaxNumberOfTransationsForFromCurrency = exchangeHistoryService.getFromCurrencyWithMaxTransactionCount();
        
        model.addAttribute("exchangeHistoryAll", exchangeHistoryAll);
        model.addAttribute("exchangeHistoryForFromCurrency", exchangeHistoryForFromCurrency);
        model.addAttribute("exchangeHistoryNumberOfTransationsForEachFromCurrency", exchangeHistoryNumberOfTransationsForEachFromCurrency);
        model.addAttribute("exchangeHistoryMaxNumberOfTransationsForFromCurrency", exchangeHistoryMaxNumberOfTransationsForFromCurrency);
        
        return "exchange-history";
    }
    
    @RequestMapping(value = "/add-data", method = RequestMethod.GET)
    public String addData(HttpServletRequest request, ModelMap model) {
        List<ExchangeRate> list = rateService.addData();
        
        model.addAttribute("request", servletContext.getContextPath());
        
        model.addAttribute("list", list);
        
        return "add-data";
    }
    
}
