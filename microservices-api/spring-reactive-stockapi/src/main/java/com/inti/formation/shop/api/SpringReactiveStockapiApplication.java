package com.inti.formation.shop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.inti.formation.shop.api.producer.KafkaConfiguration;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@Configuration
@EnableScheduling
public class SpringReactiveStockapiApplication extends KafkaConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveStockapiApplication.class, args);
	}

}
