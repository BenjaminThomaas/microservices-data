package com.inti.formation.shop.api.repository;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.inti.formation.shop.api.repository.model.Stock;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStockRepository extends ReactiveMongoRepository<Stock, Long>{

	
	
	
	Flux<Stock> findByMagasin(String magasin);
	
	
//	@Query(value= "{'$and' : [ {'active':'true'}, {'date':{$gte: ?0}}]}")
//	Flux<Stock> searchActifByDate (final boolean active, final Date date);
//	
	
	@Query("{ 'date' : {$gte:?0},'active':true }")
    Flux<Stock> searchActifByDate(Date date);
	
//	Mono<Stock> deleteBy_idstock (Long _idstock);
//	Mono<Stock> findBy_idstock (Long _idstock);
	
	
	
	
}
