package com.inti.formation.shop.api.service;

import com.inti.formation.shop.api.repository.model.Stock;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IStockService {

	Mono<Stock> register(Stock stock);

	public Flux<Stock> searchId(Long _id);

	public Flux<Stock> getStocks();

	public Mono<Stock> update(Stock s);

}
