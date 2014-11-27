package com.oshurpik.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;


@Configuration
@ComponentScan(basePackages = {"com.oshurpik"})
@EnableWebMvc
@Import({PersistenceContext.class})
@PropertySource("classpath:application.properties")
public class WebAppContext {
    
    @Bean
    public UrlBasedViewResolver setupViewResolver() {
            UrlBasedViewResolver resolver = new UrlBasedViewResolver();
            resolver.setPrefix("/WEB-INF/pages/");
            resolver.setSuffix(".jsp");
            resolver.setViewClass(JstlView.class);
            return resolver;
    }
}