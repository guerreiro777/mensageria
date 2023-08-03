package com.br.mensageria.producer;

import ch.qos.logback.core.net.server.Client;
import com.br.mensageria.producer.domain.request.ClienteRequest;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
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
