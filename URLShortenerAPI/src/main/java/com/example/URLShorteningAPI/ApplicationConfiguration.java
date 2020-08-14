package com.example.URLShorteningAPI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("com.example.URLShortenerAPI")
public class ApplicationConfiguration {
    @Value("${baseUrl}")
    private String baseUrl;

    @Bean
    URL baseURL() throws MalformedURLException {
        return new URL(baseUrl);
    }
}
