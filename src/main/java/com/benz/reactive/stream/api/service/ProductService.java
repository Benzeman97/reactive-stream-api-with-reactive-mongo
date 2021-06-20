package com.benz.reactive.stream.api.service;

import com.benz.reactive.stream.api.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<ProductDto> getProducts();
    Mono<ProductDto> findProduct(int id);
    Mono<ProductDto> saveProduct(Mono<ProductDto> productDto);
    Mono<ProductDto> updateProduct(int id,Mono<ProductDto> productDto);
    Mono<Void> deleteProduct(int id);
    Flux<ProductDto> getProductInRange(double min,double max);
}
