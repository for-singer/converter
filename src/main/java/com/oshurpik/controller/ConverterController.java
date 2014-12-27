package com.oshurpik.controller;

import com.oshurpik.entity.Currency;
import com.oshurpik.service.ExchangeHistoryService;
import com.oshurpik.entity.ExchangeHistory;
import com.oshurpik.entity.ExchangeRate;
import com.oshurpik.service.ConverterService;
import java.io.IOException;
import java.util.HashMap;
import org.json.simple.JSONObject;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


@Controller
public class ConverterController {
    private String baseUrl = "http://localhost:8080/Converter";
    
    @Autowired
    private ExchangeHistoryService exchangeHistoryService;
    
    @Autowired
    private ConverterService converterService;

    private final RestTemplate restTemplate = new TestRestTemplate();
    
    
    @RequestMapping(value = "/", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody Map convert(@RequestParam(value="from_currency", defaultValue="") String fromCurrency, 
            @RequestParam(value="to_currency", defaultValue="") String toCurrency,
            @RequestParam(value="amount", defaultValue="") String amount) throws IOException {
        
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
        
        Map<String, Object> map = new HashMap<>();
        map.put("result", result);
        map.put("hasError", hasError);
        
        return map;
    }
    
    @RequestMapping(value = "/exchange-history", method = RequestMethod.GET, produces="application/json")
    public @ResponseBody Map exchangeHistory(@RequestParam(value="from_currency", defaultValue="") String from_currency, ModelMap model) {
        model.addAttribute("from_currency", from_currency);
            
        List<ExchangeHistory>  exchangeHistoryAll = exchangeHistoryService.list();
        List<ExchangeHistory>  exchangeHistoryForFromCurrency = exchangeHistoryService.findByFromCurrencyName(from_currency);
        List exchangeHistoryNumberOfTransationsForEachFromCurrency = exchangeHistoryService.getNumberOfTransationsForEachFromCurrency();
        List exchangeHistoryMaxNumberOfTransationsForFromCurrency = exchangeHistoryService.getFromCurrencyWithMaxTransactionCount();
        
        Map<String, Object> map = new HashMap<>();
        map.put("exchangeHistoryAll", exchangeHistoryAll);
        map.put("exchangeHistoryForFromCurrency", exchangeHistoryForFromCurrency);
        map.put("exchangeHistoryNumberOfTransationsForEachFromCurrency", exchangeHistoryNumberOfTransationsForEachFromCurrency);
        map.put("exchangeHistoryMaxNumberOfTransationsForFromCurrency", exchangeHistoryMaxNumberOfTransationsForFromCurrency);
        
        return map;
    }
    
    @RequestMapping(value = "/add-data", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Map addData(HttpServletRequest request) {
        // change base URL for tests
        ServletContext sc = request.getSession().getServletContext();
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
        String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
        if (profiles.length > 0 && profiles[0].equals("dev")) {
            baseUrl = "http://localhost:9999";
        }
        // END change base URL for tests
        
        String currency1 = "uah";
        String currency2 = "usd";        
        String currency3 = "eur";               
        
        double exchangeRate_2_1 = 12.05;
        double exchangeRate_3_1 = 18.0;
        double exchangeRate_1_2 = 0.0847;
        double exchangeRate_1_3 = 0.0568;
        
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();               
        
        jsonObject.put("name", currency1);        
        HttpEntity httpRequest = new HttpEntity(jsonObject.toString(), headers);
        ResponseEntity<Currency> returnedCurrency1 = restTemplate.postForEntity(baseUrl + "/rest/currency", httpRequest, Currency.class);
        String returnedCurrencyHref1 = returnedCurrency1.getHeaders().getLocation().toString();
        
        jsonObject.put("name", currency2);
        httpRequest = new HttpEntity(jsonObject.toString(), headers);
        ResponseEntity<Currency> returnedCurrency2 = restTemplate.postForEntity(baseUrl + "/rest/currency", httpRequest, Currency.class);
        String returnedCurrencyHref2 = returnedCurrency2.getHeaders().getLocation().toString();
        
        jsonObject.put("name", currency3);
        httpRequest = new HttpEntity(jsonObject.toString(), headers);
        ResponseEntity<Currency> returnedCurrency3 = restTemplate.postForEntity(baseUrl + "/rest/currency", httpRequest, Currency.class);
        String returnedCurrencyHref3 = returnedCurrency3.getHeaders().getLocation().toString();
        
        
        
        jsonObject = new JSONObject();
        
        jsonObject.put("fromCurrency", returnedCurrencyHref2);
        jsonObject.put("toCurrency", returnedCurrencyHref1);
        jsonObject.put("rate", String.valueOf(exchangeRate_2_1));
        httpRequest = new HttpEntity(jsonObject.toString(), headers);	        
        ResponseEntity<ExchangeRate> returnedExchangeRate1 = restTemplate.postForEntity(baseUrl + "/rest/exchange-rate", httpRequest, ExchangeRate.class);
        
        jsonObject.put("fromCurrency", returnedCurrencyHref3);
        jsonObject.put("toCurrency", returnedCurrencyHref1);
        jsonObject.put("rate", String.valueOf(exchangeRate_3_1));
        httpRequest = new HttpEntity(jsonObject.toString(), headers);	        
        ResponseEntity<ExchangeRate> returnedExchangeRate2 = restTemplate.postForEntity(baseUrl + "/rest/exchange-rate", httpRequest, ExchangeRate.class);
        
        jsonObject.put("fromCurrency", returnedCurrencyHref1);
        jsonObject.put("toCurrency", returnedCurrencyHref2);
        jsonObject.put("rate", String.valueOf(exchangeRate_1_2));
        httpRequest = new HttpEntity(jsonObject.toString(), headers);	        
        ResponseEntity<ExchangeRate> returnedExchangeRate3 = restTemplate.postForEntity(baseUrl + "/rest/exchange-rate", httpRequest, ExchangeRate.class);
        
        jsonObject.put("fromCurrency", returnedCurrencyHref1);
        jsonObject.put("toCurrency", returnedCurrencyHref3);
        jsonObject.put("rate", String.valueOf(exchangeRate_1_3));
        httpRequest = new HttpEntity(jsonObject.toString(), headers);	        
        ResponseEntity<ExchangeRate> returnedExchangeRate4 = restTemplate.postForEntity(baseUrl + "/rest/exchange-rate", httpRequest, ExchangeRate.class);  
        
        
        Map<String, Object> map = new HashMap<>();
        map.put("result", "Data was added");
        
        return map;
    }
    
}
