package com.inti.formation.data.kafka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.inti.formation.data.kafka.message.Event;
import com.inti.formation.stockRepository.StockRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ConsumerBuilder {
		@Autowired
		StockRepository stockrepo;


	@KafkaListener(topics = "${kafka.topic-name}", groupId = "${kafka.consumer-group-id}")
	public void consume(Event stock) {
		log.info("Event readed " + stock.toString() );
			
			
			stockrepo.save(stock);
	
	}
	
	
	
	
	
	
	
	
	
	
}
