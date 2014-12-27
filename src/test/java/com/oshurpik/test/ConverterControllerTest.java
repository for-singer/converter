package com.oshurpik.test;

import com.oshurpik.config.WebAppContext;
import com.oshurpik.controller.ConverterController;
import com.oshurpik.service.ConverterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppContext.class)
@ActiveProfiles("dev")
public class ConverterControllerTest {
    
    private MockMvc mockMvc;
    
    @Mock    
    private ConverterService converterServiceMock;
    
    @InjectMocks
    private ConverterController converterController;    


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);        
        
        this.mockMvc = MockMvcBuilders.standaloneSetup(converterController).build();
    }
    
    @Test
    public void testConvert() throws Exception {
        String fromCurrencyStr = "usd0";
        String toCurrencyStr = "uah0";
        String amount = "100";
        String result = "1205";
        
        when(converterServiceMock.validateParams(amount, fromCurrencyStr, toCurrencyStr)).thenReturn(null);
        
        when(converterServiceMock.convert(fromCurrencyStr, toCurrencyStr, Double.valueOf(amount))).thenReturn(result);
        
        this.mockMvc.perform(get("/")
            .param("amount", amount)
            .param("from_currency", fromCurrencyStr)
            .param("to_currency", toCurrencyStr))
            .andExpect(status().isOk())
            .andExpect(content().string("{\"result\":\"" + result + "\",\"hasError\":false}"));
        
        verify(converterServiceMock, times(1)).validateParams(amount, fromCurrencyStr, toCurrencyStr);
        verify(converterServiceMock, times(1)).convert(fromCurrencyStr, toCurrencyStr, Double.valueOf(amount));
        verifyNoMoreInteractions(converterServiceMock);
    }
}
