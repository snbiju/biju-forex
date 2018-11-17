package com.forex.forex_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.forex"})
public class ForexDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForexDemoApplication.class, args);
	}
}
