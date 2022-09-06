package com.loaning.loanrepay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LoanRepayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanRepayApplication.class, args);
	}

}
