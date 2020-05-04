package com.inti.formation.shop.api.service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;

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
	public Mono<Stock> searchId(final Long id) {
		// TODO Auto-generated method stub
		return stockRepo.findById(id);
	}

	
	
	public Flux<Stock> searchStockByMagasin( final String magasin){
		return stockRepo.findByMagasin(magasin);
	}
	
	
	public Flux<Stock> searchActiveByDate ( final Date date) {
		
		return stockRepo.searchActifByDate(true, date);
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


	public Mono<Void> deleteById(final Long id) {
		// TODO Auto-generated method stub
		return stockRepo.deleteById(id);
	}


	@Override
	public Mono<Void> delete(final Stock stock) {
		// TODO Auto-generated method stub
		return stockRepo.delete(stock);
	}



	}


