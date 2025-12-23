package com.uos.all4runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class All4RunnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(All4RunnerApplication.class, args);
	}

}
