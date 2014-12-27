package com.oshurpik.test;

import com.oshurpik.config.WebAppContext;
import com.oshurpik.entity.Currency;
import com.oshurpik.entity.ExchangeRate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;

@RunWith (SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration (classes = WebAppContext.class)
@WebAppConfiguration
@IntegrationTest("server.port:9999")
@ActiveProfiles("dev")
public class ConverterTest {
    
    private static final String BASE_URL = "http://localhost:9999";
    
    private final RestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void setup() throws Exception {
        
    }
    
    @Test
    public void testConvert() throws Exception {
        String fromCurrencyStr = "usd1";
        String toCurrencyStr = "uah1";
        Double amount = 100.0;
        Double rate = 12.05;
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();               
        
        jsonObject.put("name", fromCurrencyStr);
        HttpEntity httpRequest = new HttpEntity(jsonObject.toString(), headers);
        ResponseEntity<Currency> fromCurrency = restTemplate.postForEntity(BASE_URL + "/rest/currency", httpRequest, Currency.class);
        assertEquals(HttpStatus.CREATED, fromCurrency.getStatusCode());
        String fromCurrencyHref = fromCurrency.getHeaders().getLocation().toString();
        
        
        
        jsonObject.put("name", toCurrencyStr);
        httpRequest = new HttpEntity(jsonObject.toString(), headers);
        ResponseEntity<Currency> toCurrency = restTemplate.postForEntity(BASE_URL + "/rest/currency", httpRequest, Currency.class);
        assertEquals(HttpStatus.CREATED, toCurrency.getStatusCode());
        String toCurrencyHref = toCurrency.getHeaders().getLocation().toString();
        
        
        
        jsonObject = new JSONObject();
        jsonObject.put("fromCurrency", fromCurrencyHref);
        jsonObject.put("toCurrency", toCurrencyHref);
        jsonObject.put("rate", String.valueOf(rate));
        httpRequest = new HttpEntity(jsonObject.toString(), headers);	        
        ResponseEntity<ExchangeRate> returnedExchangeRate = restTemplate.postForEntity(BASE_URL + "/rest/exchange-rate/", httpRequest, ExchangeRate.class);
        assertEquals(HttpStatus.CREATED, returnedExchangeRate.getStatusCode());
        
        
        String result = String.valueOf(Math.round(amount * rate * 100.00) / 100.00);
        
        
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/?amount={amount}&from_currency={from_currency}&to_currency={to_currency}", String.class, String.valueOf(amount), fromCurrencyStr, toCurrencyStr);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"result\":\"" + result + "\",\"hasError\":false}", response.getBody());
    }
    
    @Test
    public void testExchangeHistory() throws Exception {
        String currencyStr1 = "uah2";
        String currencyStr2 = "usd2";
        String currencyStr3 = "eur2";
        
        String amount1 = "100";
        String amount2 = "200";
        String amount3 = "300";
        
        Double rate2_1 = 12.05;
        Double rate3_1 = 16.35;
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();               
        
        jsonObject.put("name", currencyStr1);
        HttpEntity httpRequest = new HttpEntity(jsonObject.toString(), headers);
        ResponseEntity<Currency> currency1 = restTemplate.postForEntity(BASE_URL + "/rest/currency", httpRequest, Currency.class);
        assertEquals(HttpStatus.CREATED, currency1.getStatusCode());
        String currency1Href = currency1.getHeaders().getLocation().toString();
        
        
        jsonObject.put("name", currencyStr2);
        httpRequest = new HttpEntity(jsonObject.toString(), headers);
        ResponseEntity<Currency> currency2 = restTemplate.postForEntity(BASE_URL + "/rest/currency", httpRequest, Currency.class);
        assertEquals(HttpStatus.CREATED, currency2.getStatusCode());
        String currency2Href = currency2.getHeaders().getLocation().toString();
        
        
        jsonObject.put("name", currencyStr3);
        httpRequest = new HttpEntity(jsonObject.toString(), headers);
        ResponseEntity<Currency> currency3 = restTemplate.postForEntity(BASE_URL + "/rest/currency", httpRequest, Currency.class);
        assertEquals(HttpStatus.CREATED, currency3.getStatusCode());
        String currency3Href = currency3.getHeaders().getLocation().toString();
        
        
        jsonObject = new JSONObject();
        jsonObject.put("fromCurrency", currency2Href);
        jsonObject.put("toCurrency", currency1Href);
        jsonObject.put("rate", String.valueOf(rate2_1));
        httpRequest = new HttpEntity(jsonObject.toString(), headers);	        
        ResponseEntity<ExchangeRate> returnedExchangeRate1 = restTemplate.postForEntity(BASE_URL + "/rest/exchange-rate/", httpRequest, ExchangeRate.class);
        assertEquals(HttpStatus.CREATED, returnedExchangeRate1.getStatusCode());
        
        
        jsonObject = new JSONObject();
        jsonObject.put("fromCurrency", currency3Href);
        jsonObject.put("toCurrency", currency1Href);
        jsonObject.put("rate", String.valueOf(rate3_1));
        httpRequest = new HttpEntity(jsonObject.toString(), headers);	        
        ResponseEntity<ExchangeRate> returnedExchangeRate2 = restTemplate.postForEntity(BASE_URL + "/rest/exchange-rate/", httpRequest, ExchangeRate.class);
        assertEquals(HttpStatus.CREATED, returnedExchangeRate2.getStatusCode());
        
        
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/?amount={amount}&from_currency={from_currency}&to_currency={to_currency}", String.class, String.valueOf(amount1), currencyStr2, currencyStr1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        response = restTemplate.getForEntity(BASE_URL + "/?amount={amount}&from_currency={from_currency}&to_currency={to_currency}", String.class, String.valueOf(amount1), currencyStr2, currencyStr1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        response = restTemplate.getForEntity(BASE_URL + "/?amount={amount}&from_currency={from_currency}&to_currency={to_currency}", String.class, String.valueOf(amount1), currencyStr2, currencyStr1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        response = restTemplate.getForEntity(BASE_URL + "/?amount={amount}&from_currency={from_currency}&to_currency={to_currency}", String.class, String.valueOf(amount2), currencyStr3, currencyStr1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        response = restTemplate.getForEntity(BASE_URL + "/?amount={amount}&from_currency={from_currency}&to_currency={to_currency}", String.class, String.valueOf(amount3), currencyStr3, currencyStr1);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        
        ResponseEntity<Map> response2 = restTemplate.getForEntity(BASE_URL + "/exchange-history?from_currency={from_currency}", Map.class, currencyStr2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        
        List exchangeHistoryAll = (List)response2.getBody().get("exchangeHistoryAll");        
        List exchangeHistoryAllList = new ArrayList();
        for(Object exchangeHistoryAllItem : exchangeHistoryAll) {
            exchangeHistoryAllList.add(Arrays.asList(((Map)((Map)exchangeHistoryAllItem).get("fromCurrency")).get("name"),
                    ((Map)((Map)exchangeHistoryAllItem).get("toCurrency")).get("name"),
                    String.valueOf((Double)((Map)exchangeHistoryAllItem).get("amount"))));
        }
        List exchangeHistoryAllExpectedList = new ArrayList();
        exchangeHistoryAllExpectedList.add(Arrays.asList(currencyStr2, currencyStr1, String.valueOf(Double.parseDouble(amount1))));
        exchangeHistoryAllExpectedList.add(Arrays.asList(currencyStr2, currencyStr1, String.valueOf(Double.parseDouble(amount1))));
        exchangeHistoryAllExpectedList.add(Arrays.asList(currencyStr2, currencyStr1, String.valueOf(Double.parseDouble(amount1))));
        exchangeHistoryAllExpectedList.add(Arrays.asList(currencyStr3, currencyStr1, String.valueOf(Double.parseDouble(amount2))));
        exchangeHistoryAllExpectedList.add(Arrays.asList(currencyStr3, currencyStr1, String.valueOf(Double.parseDouble(amount3))));
        exchangeHistoryAllExpectedList.removeAll(exchangeHistoryAllList);
        assertEquals(0, exchangeHistoryAllExpectedList.size());
        
        
        List exchangeHistoryForFromCurrency = (List)response2.getBody().get("exchangeHistoryForFromCurrency");        
        List exchangeHistoryForFromCurrencyList = new ArrayList();
        for(Object exchangeHistoryForFromCurrencyItem : exchangeHistoryForFromCurrency) {
            exchangeHistoryForFromCurrencyList.add(Arrays.asList(((Map)((Map)exchangeHistoryForFromCurrencyItem).get("fromCurrency")).get("name"),
                    ((Map)((Map)exchangeHistoryForFromCurrencyItem).get("toCurrency")).get("name"),
                    String.valueOf((Double)((Map)exchangeHistoryForFromCurrencyItem).get("amount"))));
        }
        List exchangeHistoryForFromCurrencyExpectedList = new ArrayList();
        exchangeHistoryForFromCurrencyExpectedList.add(Arrays.asList(currencyStr2, currencyStr1, String.valueOf(Double.parseDouble(amount1))));
        exchangeHistoryForFromCurrencyExpectedList.add(Arrays.asList(currencyStr2, currencyStr1, String.valueOf(Double.parseDouble(amount1))));
        exchangeHistoryForFromCurrencyExpectedList.add(Arrays.asList(currencyStr2, currencyStr1, String.valueOf(Double.parseDouble(amount1))));
        exchangeHistoryForFromCurrencyExpectedList.removeAll(exchangeHistoryForFromCurrencyList);
        assertEquals(0, exchangeHistoryForFromCurrencyExpectedList.size());
        
        
        List exchangeHistoryNumberOfTransationsForEachFromCurrency = (List)response2.getBody().get("exchangeHistoryNumberOfTransationsForEachFromCurrency");
        List exchangeHistoryNumberOfTransationsForEachFromCurrencyExpected = new ArrayList();
        exchangeHistoryNumberOfTransationsForEachFromCurrencyExpected.add(Arrays.asList("usd2","uah2",3));
        exchangeHistoryNumberOfTransationsForEachFromCurrencyExpected.add(Arrays.asList("eur2","uah2",2));        
        exchangeHistoryNumberOfTransationsForEachFromCurrencyExpected.removeAll(exchangeHistoryNumberOfTransationsForEachFromCurrency);
        assertEquals(0, exchangeHistoryNumberOfTransationsForEachFromCurrencyExpected.size());
        
        
        Object exchangeHistoryMaxNumberOfTransationsForFromCurrency = response2.getBody().get("exchangeHistoryMaxNumberOfTransationsForFromCurrency");
        assertEquals(exchangeHistoryMaxNumberOfTransationsForFromCurrency, Arrays.asList(Arrays.asList("usd2","uah2",3)));        
    }
    
    @Test
    public void testAddData() throws Exception {        
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL + "/add-data", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("{\"result\":\"Data was added\"}", response.getBody());
    }
}
