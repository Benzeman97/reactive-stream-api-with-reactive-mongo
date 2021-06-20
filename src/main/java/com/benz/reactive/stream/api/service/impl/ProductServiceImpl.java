package com.benz.reactive.stream.api.service.impl;

import com.benz.reactive.stream.api.dao.ProductDao;
import com.benz.reactive.stream.api.dto.ProductDto;
import com.benz.reactive.stream.api.service.ProductService;
import com.benz.reactive.stream.api.utils.ProductUtils;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao){
        this.productDao=productDao;
    }

    @Override
    public Flux<ProductDto> getProducts(){
        return productDao.findAll()
//                .delayElements(Duration.ofSeconds(2))
//                .doOnNext(p-> System.out.println("processing "+p.getProdId()))
                .map(prod-> ProductUtils.entityToDto(prod)); //1 sort
    }


    @Override
    public Mono<ProductDto> findProduct(int id) {
        return productDao.findById(id)
                   .map(prod->ProductUtils.entityToDto(prod));// prod id
    }

    @Override
    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDto) {
        return productDto.map(prod->ProductUtils.dtoToEntity(prod))
                .flatMap(prod->productDao.save(prod))
                .map(prod->ProductUtils.entityToDto(prod));
    }

    @Override
    public Mono<ProductDto> updateProduct(int id, Mono<ProductDto> productDto) {
          return productDao.findById(id)
                  .flatMap(prod->productDto.map(p->ProductUtils.dtoToEntity(p))).
                          doOnNext(p->p.setProdId(id))
                  .flatMap(p->productDao.save(p))
                              .map(p->ProductUtils.entityToDto(p));

    }

    @Override
    public Mono<Void> deleteProduct(int id) {
        return productDao.findById(id)
                  .flatMap(prod-> productDao.delete(prod));

    }

    @Override
    public Flux<ProductDto> getProductInRange(double min, double max) {
        return productDao.findByPriceBetween(Range.closed(min,max));
    }


}
