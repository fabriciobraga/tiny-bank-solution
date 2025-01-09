package com.tinybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.tinybank.model")
public class TinyBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinyBankApplication.class, args);
	}

}
