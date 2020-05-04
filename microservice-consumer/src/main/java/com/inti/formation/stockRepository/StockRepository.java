package com.inti.formation.stockRepository;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Repository;

import com.inti.formation.data.kafka.message.Event;

import java.util.List;

@Repository
public interface StockRepository extends ElasticsearchCrudRepository<Event, Long> {
    
}
