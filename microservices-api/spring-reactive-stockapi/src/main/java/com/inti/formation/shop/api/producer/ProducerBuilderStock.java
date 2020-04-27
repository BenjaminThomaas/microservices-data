package com.inti.formation.shop.api.producer;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;


/**
 * 
 * @author Sylvanius Kouandongui
 *
 */
@Slf4j
@Component
public class ProducerBuilderStock {

    @Value("${kafka.topic-name}")
    private String TOPIC;
    
    

    @Autowired
    private KafkaTemplate<String, EventStock> kafkaTemplate;

    @Value("${kafka.compression-type}")
    private String compressionType;

    // Préparation de la donnée :
    //milliseconds
    @Scheduled(fixedDelayString = "${schedule-time}")
    public void scheduleFixedDelayTask() {
        Long _id;
        EventStock event = new EventStock();
        event.setIdproduct( idproduct.toString());
//        event.setDescritpion( "ceci est un objet d'id :   " + idproduct);
//        event.setLibelle("mon objet");
//        event.setOrigine("Normandie");
//        
//        
        Integer prix = new  Random().ints(1,(250+1)).findFirst().getAsInt();
        
        event.setPrix(prix.toString());
        
        
        
        
        
        
        //envoi du message :
        
        ProducerRecord<String, EventStock> producerRecord = new ProducerRecord<>(TOPIC, 1, event.getIdproduct(),
                event);
	    kafkaTemplate.send(producerRecord);
    }
    //instancie un producerrecord 
    /*
     * les paramètre sont :
     * 1) le nom du topic  ==> TOPIC
     * 2) clé du message dans kafka   ==> event.getCode()
     * 3) la value   ==> event
     * !! ne pas gérer les partitions par soit meme et juste donné les clé du message a kafka
     */

}
