package com.example.demoApp1.runner;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.demoApp1.DemoApp1Application;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = DemoApp1Application.class)
public class CucumberConfig {

	@Bean
	 public RestTemplate testRestTemplate() {
        return new RestTemplate();
    }
}
