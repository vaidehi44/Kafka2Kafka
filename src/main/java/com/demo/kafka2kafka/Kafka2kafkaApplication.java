package com.demo.kafka2kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class Kafka2kafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(Kafka2kafkaApplication.class, args);
	}

}
