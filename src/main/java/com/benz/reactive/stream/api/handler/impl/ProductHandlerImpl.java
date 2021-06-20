package com.benz.reactive.stream.api.handler.impl;

import com.benz.reactive.stream.api.dao.ProductDao;
import com.benz.reactive.stream.api.dto.ProductDto;
import com.benz.reactive.stream.api.handler.ProductHandler;
import com.benz.reactive.stream.api.utils.ProductUtils;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ProductHandlerImpl implements ProductHandler {

    private ProductDao productDao;

    public ProductHandlerImpl(ProductDao productDao){
        this.productDao=productDao;
    }

    @Override
    public Mono<ServerResponse> getProducts(ServerRequest request) {

        Flux<ProductDto> products = productDao.findAll(Sort.by("price").descending())
                .delayElements(Duration.ofSeconds(2))
                .doOnNext(p-> System.out.println("processing "+p.getProdId()))
                .map(prod-> ProductUtils.entityToDto(prod));

        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(products,ProductDto.class);
    }

    @Override
    public Mono<ServerResponse> findProduct(ServerRequest request) {
        int prodId = Integer.valueOf(request.pathVariable("id"));

         Mono<ProductDto> product = productDao.findById(prodId)
                     .map(prod->ProductUtils.entityToDto(prod));
         return ServerResponse.ok().body(product,ProductDto.class);
    }

    @Override
    public Mono<ServerResponse> saveProduct(ServerRequest request) {
        Mono<ProductDto> productDto = request.bodyToMono(ProductDto.class);

          Mono<ProductDto> product = productDto.map(p->ProductUtils.dtoToEntity(p))
                   .flatMap(prod->productDao.save(prod))
                   .map(p->ProductUtils.entityToDto(p));

          return ServerResponse.status(HttpStatus.CREATED).body(product,ProductDto.class);
    }

    @Override
    public Mono<ServerResponse> updateProduct(ServerRequest request) {

        int prodId = Integer.valueOf(request.pathVariable("id"));
        Mono<ProductDto> productDto = request.bodyToMono(ProductDto.class);

          Mono<ProductDto> prduct = productDao.findById(prodId)
                  .flatMap(p->productDto.map(prod->ProductUtils.dtoToEntity(prod)))
                   .doOnNext(p->p.setProdId(prodId))
                   .flatMap(p->productDao.save(p))
                   .map(p->ProductUtils.entityToDto(p));

          return ServerResponse.status(HttpStatus.CREATED).body(prduct,ProductDto.class);
    }

    @Override
    public Mono<ServerResponse> deleteProduct(ServerRequest request) {
          int prodId = Integer.valueOf(request.pathVariable("id"));

         Mono<Void> deletedProduct =  productDao.findById(prodId)
                     .flatMap(p->productDao.delete(p));

         return ServerResponse.ok().body(deletedProduct,ProductDto.class);
    }

    @Override
    public Mono<ServerResponse> getProductInRange(ServerRequest request) {
        double min = Double.valueOf(request.queryParam("min").orElse("10000"));
        double max = Double.valueOf(request.queryParam("max").orElse("50000"));

            Flux<ProductDto> products = productDao.findByPriceBetween(Range.closed(min,max))
                     .map(p->ProductUtils.entityToDto(p));
                  return ServerResponse.ok().body(products,ProductDto.class);

    }


}
