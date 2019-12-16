package com.sfelaco.rsocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableR2dbcRepositories
public class RSocketServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RSocketServerApplication.class, args);
	}

}
