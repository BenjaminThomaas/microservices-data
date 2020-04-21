package com.inti.formation.shop.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inti.formation.shop.api.repository.IStockRepository;
import com.inti.formation.shop.api.repository.model.Stock;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class StockServiceImpl implements IStockService{
	
	@Autowired
	private IStockRepository stockRepo;
	
	
	@Override
	public Mono<Stock> register(final Stock stock) {
		// TODO Auto-generated method stub
		return stockRepo.save(stock);
	}

	
	//Methodes find spéciales
	
	@Override
	public Flux<Stock> searchId(final Long _id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<Stock> getStocks() {
		// TODO Auto-generated method stub
		return stockRepo.findAll();
	}

	@Override
	public Mono<Stock> update(final Stock s) {
		// TODO Auto-generated method stub
		return stockRepo.save(s);
	}

}
