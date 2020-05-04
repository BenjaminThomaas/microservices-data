package com.inti.formation.shop.api;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.function.Consumer;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.inti.formation.shop.api.producer.EventStock;
import com.inti.formation.shop.api.producer.KafkaConfiguration;
import com.inti.formation.shop.api.repository.model.Stock;
import com.inti.formation.shop.api.rest.bean.StockRequest;
import com.inti.formation.shop.api.rest.exception.InternalServerException;
import com.inti.formation.shop.api.rest.exception.ValidationParameterException;
import com.inti.formation.shop.api.service.IStockService;
import com.inti.formation.shop.api.service.StockServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value= "/v1/shop")
@Slf4j
public class EndPoint extends KafkaConfiguration{

	@Autowired
	StockServiceImpl stockservice;
	
	@Value("${kafka.topic-name}")
    private String TOPIC;
    
    

    @Autowired
    private KafkaTemplate<Long, Stock> kafkaTemplate;

    @Value("${kafka.compression-type}")
    private String compressionType;

	
	@ExceptionHandler(ValidationParameterException.class)
    public Mono<ResponseEntity<String>> handlerValidationParameterException(ValidationParameterException e) {
     return Mono.just(
                badRequest().body("Missing parameter: "+ e.getMessage()));
    }

    @ExceptionHandler(InternalServerException.class)
    public Mono<ResponseEntity<String>> handlerInternalServerException() {
        return Mono.just(status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error server has occurred "));
    }
	
    @PostMapping(value = "/registerstock" , headers = "Accept=application/json; charset=utf-8")
    @ResponseStatus( value  = HttpStatus.CREATED, reason="Stock is registered" )
    public Mono<String> create(@RequestBody StockRequest stock) {
        // Vérification des paramètres
        if( ObjectUtils.anyNotNull(stock)  && !ObjectUtils.allNotNull(stock.getId(), stock.getMagasin(), stock.getDate(), stock.getIdproduct())){
            log.error("Validation error: one of attributes is not found");
            return Mono.error(new ValidationParameterException("(Validation error message): one of attributes is not found" ));
        }
        return Mono.just(stock)
        .map(data->
                {

                    return stockservice.register( data).subscribe().toString();

                });
    }
	
    
    @GetMapping
    @RequestMapping(value = "/stocks/{magasin}")

    public Flux<Stock> getStockByMagasin(@RequestParam(required = true, name = "magasin") String magasin ) {
        log.info("Searching  {} ",magasin );
        return stockservice.searchStockByMagasin(magasin)

                // uses of doNext

                .doOnNext(stock -> log.info(stock.getMagasin()+ " is found"));

    }
    
    
    
    @GetMapping
    @RequestMapping(value = "/actives")
    public Flux<Stock> getActivesByDate(@RequestParam(required = true, name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX") Date date ) {
        log.info("Searching  {} ",date );
        return stockservice.searchActiveByDate(date);

                // uses of doNext

//                .doOnNext(stock -> log.info(stock.getDate()+ " is found"));

    }
    
    
    
    
    
    
    @GetMapping
    @RequestMapping(value = "/Stocks/")
    public Flux<Stock> getstocks() {
        log.info("All stocks searching");
      return stockservice.getStocks()
              // uses of map
                .switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map( data-> data);
    }
    
    
//    @DeleteMapping
//    @RequestMapping(value = "/DeleteStock/{_id}")
//    public Mono<Void> deleteBy_id(@RequestParam(required = true, name = "_id") Long _id ) {
//        log.info("Searching  {} ",_id );
//        
//        Mono <EventStock> stock = stockservice.searchId(_id);
//
//                // uses of doNext
//        
//        ProducerRecord<Long, Stock> producerRecord = new ProducerRecord<>(TOPIC, stock. )
////	    kafkaTemplate.send(producerRecord);
//        return stockservice.deleteById(_id);
//}
     
  @DeleteMapping
  @RequestMapping(value = "/DeleteStock")
  public Mono<Void> delete(@RequestBody Stock stock ) {
	  Date localDate = new Date();
      stock.setDatesupp(localDate);
ProducerRecord<Long, Stock> producerRecord = new ProducerRecord<>(TOPIC, stock.getId(), stock );
kafkaTemplate.send(producerRecord);
      
     
      return stockservice.deleteById(stock.getId());
    		 
    		  
       
}
    	
    
//    @DeleteMapping(value = "/deletestock/{_id}" , headers = "Accept=application/json; charset=utf-8")
//    @ResponseStatus( value  = HttpStatus.CREATED, reason="Stock is deleted" )
//    public Mono<Void> deleteById (@RequestParam(required = true, name = "_id") Long _id) {
//       
//        
//        return Mono.just(stock)
//        .map(stock->
//                {
////                	ProducerRecord<String, EventStock> producerRecord = new ProducerRecord<>(TOPIC, s.get_id(), s);
////                	kafkaTemplate.send(producerRecord);
//                    return stockservice.delete( stock).subscribe().toString();
//
//                });
//    }
//    
//   

    
    
}
    
   

