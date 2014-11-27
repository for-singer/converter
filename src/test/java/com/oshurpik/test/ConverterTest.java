package com.oshurpik.test;

import com.oshurpik.config.WebAppContext;
import com.oshurpik.entity.Currency;
import com.oshurpik.entity.ExchangeHistory;
import com.oshurpik.entity.ExchangeRate;
import com.oshurpik.entity.ExchangeRatePK;
import com.oshurpik.repository.CurrencyRepository;
import com.oshurpik.repository.ExchangeHistoryRepository;
import com.oshurpik.repository.ExchangeRateRepository;
import java.util.Date;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppContext.class)
@ActiveProfiles("dev")
public class ConverterTest {
    
    private MockMvc mockMvc;
    
    @Autowired
    private CurrencyRepository currencyRepository;
    
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    
    @Autowired
    private ExchangeHistoryRepository exchangeHistoryRepository;
    
    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        
        exchangeRateRepository.deleteAll();
        exchangeHistoryRepository.deleteAll();
        currencyRepository.deleteAll();
        
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
    
    @Test
    public void testConvert() throws Exception {
        String fromCurrencyStr = "usd";
        String toCurrencyStr = "uah";
        String amount = "100";
        Double rate = 12.05;
        
        Currency fromCurrency = new Currency(fromCurrencyStr);
        Currency toCurrency = new Currency(toCurrencyStr);
        
        currencyRepository.save(fromCurrency);
        currencyRepository.save(toCurrency);
        
        ExchangeRate exchangeRate = new ExchangeRate(new ExchangeRatePK(fromCurrency, toCurrency), 12.05);
        
        exchangeRateRepository.save(exchangeRate);
        
        ExchangeHistory exchangeHistory = new ExchangeHistory(fromCurrency, toCurrency, Double.valueOf(amount), new Date());
        
        exchangeHistoryRepository.save(exchangeHistory);        
        
        String result = String.valueOf(Math.round(Double.valueOf(amount) * rate * 100.00) / 100.00);
        
        this.mockMvc.perform(get("/")
            .param("amount", amount)
            .param("from_currency", fromCurrencyStr)
            .param("to_currency", toCurrencyStr))
            .andExpect(status().isOk())
            .andExpect(view().name("converter"))
            .andExpect(forwardedUrl("/WEB-INF/pages/converter.jsp"))
            .andExpect(model().attribute("hasError", false))
            .andExpect(model().attribute("result", result));
    }
    
    @Test
    public void testExchangeHistory() throws Exception {
        String currencyStr1 = "uah";
        String currencyStr2 = "usd";
        String currencyStr3 = "eur";
        
        String amount1 = "100";
        String amount2 = "200";
        String amount3 = "300";
        
        Double rate1 = 12.05;
        Double rate2 = 16.35;
        
        Currency currency1 = new Currency(currencyStr1);
        Currency currency2 = new Currency(currencyStr2);
        Currency currency3 = new Currency(currencyStr3);
        
        currencyRepository.save(currency1);
        currencyRepository.save(currency2);
        currencyRepository.save(currency3);
        
        ExchangeRate exchangeRate1 = new ExchangeRate(new ExchangeRatePK(currency2, currency1), rate1);
        ExchangeRate exchangeRate2 = new ExchangeRate(new ExchangeRatePK(currency3, currency1), rate2);
        
        exchangeRateRepository.save(exchangeRate1);
        exchangeRateRepository.save(exchangeRate2);
        
        Date date1 = new Date();
        
        ExchangeHistory exchangeHistory1 = new ExchangeHistory(currency2, currency1, Double.valueOf(amount1), date1);
        ExchangeHistory exchangeHistory2 = new ExchangeHistory(currency2, currency1, Double.valueOf(amount1), new Date());
        ExchangeHistory exchangeHistory3 = new ExchangeHistory(currency3, currency1, Double.valueOf(amount1), new Date());
        ExchangeHistory exchangeHistory4 = new ExchangeHistory(currency3, currency1, Double.valueOf(amount2), new Date());
        ExchangeHistory exchangeHistory5 = new ExchangeHistory(currency3, currency1, Double.valueOf(amount3), new Date());

        exchangeHistoryRepository.save(exchangeHistory1);
        exchangeHistoryRepository.save(exchangeHistory2);
        exchangeHistoryRepository.save(exchangeHistory3);
        exchangeHistoryRepository.save(exchangeHistory4);
        exchangeHistoryRepository.save(exchangeHistory5);

        
//        String result = String.valueOf(Math.round(Double.valueOf(amount) * rate * 100.00) / 100.00);
        
        this.mockMvc.perform(get("/exchange-history")
            .param("from_currency", currencyStr2))
            .andExpect(status().isOk())
            .andExpect(view().name("exchange-history"))
            .andExpect(forwardedUrl("/WEB-INF/pages/exchange-history.jsp"))
                
            .andExpect(model().attribute("exchangeHistoryAll", hasSize(5)))
        
                .andExpect(model().attribute("exchangeHistoryAll", hasItem(
                        allOf(
                                hasProperty("fromCurrency", is(currency2)),
                                hasProperty("toCurrency", is(currency1)),
                                hasProperty("amount", is(Double.valueOf(amount1)))
                        )
                )))
                .andExpect(model().attribute("exchangeHistoryAll", hasItem(
                        allOf(
                                hasProperty("fromCurrency", is(currency2)),
                                hasProperty("toCurrency", is(currency1)),
                                hasProperty("amount", is(Double.valueOf(amount1)))
                        )
                )))
                .andExpect(model().attribute("exchangeHistoryAll", hasItem(
                        allOf(
                                hasProperty("fromCurrency", is(currency3)),
                                hasProperty("toCurrency", is(currency1)),
                                hasProperty("amount", is(Double.valueOf(amount1)))
                        )
                )))
                .andExpect(model().attribute("exchangeHistoryAll", hasItem(
                        allOf(
                                hasProperty("fromCurrency", is(currency3)),
                                hasProperty("toCurrency", is(currency1)),
                                hasProperty("amount", is(Double.valueOf(amount2)))
                        )
                )))
                .andExpect(model().attribute("exchangeHistoryAll", hasItem(
                        allOf(
                                hasProperty("fromCurrency", is(currency3)),
                                hasProperty("toCurrency", is(currency1)),
                                hasProperty("amount", is(Double.valueOf(amount3)))
                        )
                )))
            .andExpect(model().attribute("exchangeHistoryForFromCurrency", hasSize(2)))        
                .andExpect(model().attribute("exchangeHistoryForFromCurrency", hasItem(
                        allOf(
                                hasProperty("fromCurrency", is(currency2)),
                                hasProperty("toCurrency", is(currency1)),
                                hasProperty("amount", is(Double.valueOf(amount1)))
                        )
                )))
                .andExpect(model().attribute("exchangeHistoryForFromCurrency", hasItem(
                        allOf(
                                hasProperty("fromCurrency", is(currency2)),
                                hasProperty("toCurrency", is(currency1)),
                                hasProperty("amount", is(Double.valueOf(amount1)))
                        )
                )))
            .andExpect(model().attribute("exchangeHistoryNumberOfTransationsForEachFromCurrency", hasSize(2)))
                .andExpect(model().attribute("exchangeHistoryNumberOfTransationsForEachFromCurrency",  hasItem(
                        new Object[] {currencyStr3, currencyStr1, (long)3}
                )))
                .andExpect(model().attribute("exchangeHistoryNumberOfTransationsForEachFromCurrency",  hasItem(
                        new Object[] {currencyStr2, currencyStr1, (long)2}
                )))    
            .andExpect(model().attribute("exchangeHistoryMaxNumberOfTransationsForFromCurrency", hasSize(1)))
                .andExpect(model().attribute("exchangeHistoryMaxNumberOfTransationsForFromCurrency",  hasItem(
                        new Object[] {currencyStr3, currencyStr1, (long)3}
                )));
        
    }
    
    @Test
    public void testAddData() throws Exception {
        String currencyStr1 = "uah";
        String currencyStr2 = "usd";
        String currencyStr3 = "eur";
        
        Double rate1 = 12.05;
        Double rate2 = 18.0;
        Double rate3 = 0.0847;
        Double rate4 = 0.0568;
        
        this.mockMvc.perform(get("/add-data"))
            .andExpect(status().isOk())
            .andExpect(view().name("add-data"))
            .andExpect(forwardedUrl("/WEB-INF/pages/add-data.jsp"));

        
        
        assertEquals(3, currencyRepository.findAll().size());
        
        assertEquals(currencyStr1, currencyRepository.findAll().get(0).getName());
        assertEquals(currencyStr2, currencyRepository.findAll().get(1).getName());
        assertEquals(currencyStr3, currencyRepository.findAll().get(2).getName());
        
        
        
        assertEquals(4, exchangeRateRepository.findAll().size());
        
        assertEquals(currencyStr2, exchangeRateRepository.findAll().get(0).getId().getFromCurrency().getName());
        assertEquals(currencyStr1, exchangeRateRepository.findAll().get(0).getId().getToCurrency().getName());
        assertEquals(rate1, (Double)exchangeRateRepository.findAll().get(0).getRate());
        
        assertEquals(currencyStr3, exchangeRateRepository.findAll().get(1).getId().getFromCurrency().getName());
        assertEquals(currencyStr1, exchangeRateRepository.findAll().get(1).getId().getToCurrency().getName());
        assertEquals(rate2, (Double)exchangeRateRepository.findAll().get(1).getRate());
        
        assertEquals(currencyStr1, exchangeRateRepository.findAll().get(2).getId().getFromCurrency().getName());
        assertEquals(currencyStr2, exchangeRateRepository.findAll().get(2).getId().getToCurrency().getName());
        assertEquals(rate3, (Double)exchangeRateRepository.findAll().get(2).getRate());
        
        assertEquals(currencyStr1, exchangeRateRepository.findAll().get(3).getId().getFromCurrency().getName());
        assertEquals(currencyStr3, exchangeRateRepository.findAll().get(3).getId().getToCurrency().getName());
        assertEquals(rate4, (Double)exchangeRateRepository.findAll().get(3).getRate());
    }
}
