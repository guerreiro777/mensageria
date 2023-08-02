package com.br.mensageria.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MensageriaProducerApplication {


	public static void main(String[] args) {
		SpringApplication.run(MensageriaProducerApplication.class, args);
	}

}
