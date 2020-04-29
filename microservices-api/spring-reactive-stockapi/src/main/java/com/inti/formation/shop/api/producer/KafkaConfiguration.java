package com.inti.formation.shop.api.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.inti.formation.shop.api.repository.model.Stock;

public class KafkaConfiguration {
	  @Value("${kafka.bootstrap-server}")
	    private String kafkaBrokerUrl;

	    @Value("${kafka.acks}")
	    private String acks;

	    @Value("${kafka.retries}")
	    private String retries;

	    @Value("${kafka.buffer-memory}")
	    private String bufferMemory;

	    @Value("${kafka.batch-size}")
	    private String batchSize;

	    @Value("${kafka.client-id}")
	    private String clientId;

	    @Value("${kafka.compression-type}")
	    private String compressionType;

	    @Value("${kafka.user}")
	    private String user;

	    @Value("${kafka.password}")
	    private String password;

//	    @Value("${kafka.max-in-flight-requests-per-connection}")
//	    private String maxRequestConnection;
//	    
//	    @Value("${kafka.auto-register-schemas}")
//	    private String autoRegisterSchemas;
	    
	    
	    
	    //partie sécurité
	    private static final String SECURITY_PROTOCOL = "security.protocol";
	    private static final String SASL_MECHANISM = "sasl.mechanism";
	    private static final String SASL_JAAS_CONFIG = "sasl.jaas.config";

	    

	    // Kafka configuration
	    @Bean
	    public Map<String, Object> producerConfigs() {
		Map<String, Object> conf = new HashMap<>();
		//création d'une hash map qui stock les configue
		
		conf.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokerUrl);
		//j'affect la valuer des varible def dans fichier app yml au producer 
		
		
		conf.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
		// on a need de spé la classe de sérialization de clé (ici mis string)(string,avro ou json)
		
		
		conf.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonPOJOSerializer.class.getName());
		// def le type de la value (ici s) 
		
		
		conf.put(ProducerConfig.RETRIES_CONFIG, retries);
		// If the request fails, the producer can automatically retry,
		//retries = nb de tentive d'esaie en cas d'echec d'iinsertion des données mieux vaut mettre 2147483647
		
		conf.put(ProducerConfig.ACKS_CONFIG, acks);
		// l'acquittemnt : nb de replication de la donnée sur les serveur en respectant min.sync.replicas
		
		
		//****************************************************
//		conf.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxRequestConnection);
		
		
		
		
		
		
		conf.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
		conf.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
		
		
		conf.put(ProducerConfig.CLIENT_ID_CONFIG, clientId);
		//identifiant producer 
		
		
		conf.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, compressionType); // lz4 compression mode unused cpu
		
		
		
		// Authentification
		//conf.put(SECURITY_PROTOCOL, "SASL_PLAINTEXT");
		//conf.put(SASL_MECHANISM, "SCRAM-SHA-512");
		//conf.put(SASL_JAAS_CONFIG, "org.apache.kafka.common.security.scram.ScramLoginModule required username=" + user
		//	+ " password=" + password + ";");
		
		
		conf.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, 1);
		//nb de threadenvoyer 
		
		
		
		
		conf.put(StreamsConfig.EXACTLY_ONCE, true); // controls that the message is send one time when ack is all
		return conf;
	    }
	//s'assure que les donner sont envoyer quand le acks = all
	    
	    
	    
	    
	    @Bean
	    public ProducerFactory<Long, Stock> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	    }

	    /*
	     * met le type de clé et le type de donnée = config le producer 
	     * 
	     */
	    
	    
	    
	    
	    
	    @Bean
	    public KafkaTemplate<Long, Stock> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	    }

}
