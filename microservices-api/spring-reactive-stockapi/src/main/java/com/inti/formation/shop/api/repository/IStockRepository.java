package com.inti.formation.shop.api.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.inti.formation.shop.api.repository.model.Stock;

public interface IStockRepository extends ReactiveMongoRepository<Stock, Long>{

	
	// Query a faire :
	
	
	/*
	 * recherche des stocks actifs par rapport à une date ?
	 * ou 
	 * recherche des stocks actifs et recherche par dates ?
	 * 
	 * get allMagasin
	 * 
	 * saveStock()
	 * updateStock()
	 * 
	 * 
	 * puis adapter dans IstockService
	 */
	
	
	
	
	
}
