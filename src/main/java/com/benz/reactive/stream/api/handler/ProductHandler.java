package com.benz.reactive.stream.api.handler;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface ProductHandler {

    Mono<ServerResponse> getProducts(ServerRequest request);
    Mono<ServerResponse> findProduct(ServerRequest request);
    Mono<ServerResponse> saveProduct(ServerRequest request);
    Mono<ServerResponse> updateProduct(ServerRequest request);
    Mono<ServerResponse> deleteProduct(ServerRequest request);
    Mono<ServerResponse> getProductInRange(ServerRequest request);
}
