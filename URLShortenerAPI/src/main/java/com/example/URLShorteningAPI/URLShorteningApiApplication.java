package com.example.URLShorteningAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.*"})
@EnableJpaRepositories(basePackages = {"com.example.URLShorteningAPI.repository"})
@EntityScan(basePackages = {"com.example.URLShorteningAPI.model"})
public class URLShorteningApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(URLShorteningApiApplication.class, args);
	}
}
