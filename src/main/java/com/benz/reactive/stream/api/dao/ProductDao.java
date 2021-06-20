package com.benz.reactive.stream.api.dao;

import com.benz.reactive.stream.api.dto.ProductDto;
import com.benz.reactive.stream.api.entity.Product;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductDao extends ReactiveMongoRepository<Product,Integer> {

    Flux<Product> findByPriceBetween(Range<Double> range);
}
