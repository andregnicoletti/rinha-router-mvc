package com.nicoletti.rinharouter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RinhaRouterMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(RinhaRouterMvcApplication.class, args);
	}

}
