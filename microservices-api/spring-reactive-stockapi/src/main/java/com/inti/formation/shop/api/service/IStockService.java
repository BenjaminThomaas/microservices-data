package com.inti.formation.shop.api.service;

import com.inti.formation.shop.api.repository.model.Stock;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStockService {

	Mono<Stock> register(Stock stock);

	public Mono<Stock> searchId(Long id);

	public Flux<Stock> getStocks();

	public Mono<Stock> update(Stock s);
	
	public Mono<Void> deleteById (Long id);
	
	public Mono<Void> delete(Stock stock);

}
