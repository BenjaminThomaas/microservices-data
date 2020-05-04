package com.inti.formation.data.kafka.config;

import java.util.HashMap;
import java.util.Map;

import com.inti.formation.data.kafka.message.Event;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
@EnableKafka
public class KafkaConsumerConfiguration {

	@Value("${kafka.boot-server}")
	private String kafkaServer;
	//récup les config du fichier yml

	@Value("${kafka.consumer-group-id}")
	private String kafkaGroupId;

	@Value("${kafka.client-id}")
	private String applicationId;

	@Bean
	public ConsumerFactory<Long, Event> consumerConfig() {
//							key   mess kafka
		Map<String, Object> configuration = new HashMap<>();
		configuration.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		configuration.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
		// GROUPID !!imp!!  
		configuration.put(ConsumerConfig.CLIENT_ID_CONFIG, applicationId);
		//earliest, latest, none ( exception)
		configuration.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		//il existe 3 mode  
		/*
		 * voir notes 
		 * 
		 */
		
		configuration.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		configuration.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");
		// Session active, rebalancing new consumer joined or eave the group
		configuration.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "300");
		configuration.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
		configuration.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(configuration, null, new JsonDeserializer<>(Event.class));
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Long, Event>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Long, Event> listener = new ConcurrentKafkaListenerContainerFactory<>();
		listener.setConsumerFactory(consumerConfig());
		return listener;
	}

}
