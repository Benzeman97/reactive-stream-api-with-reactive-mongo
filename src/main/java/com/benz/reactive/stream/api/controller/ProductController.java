package com.benz.reactive.stream.api.controller;

import com.benz.reactive.stream.api.dto.ProductDto;
import com.benz.reactive.stream.api.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Flux<ProductDto>> getProducts(){
          return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<ProductDto>> findProduct(@PathVariable int id){
         return (Objects.isNull(id)) ?
                 new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                         new ResponseEntity<>(productService.findProduct(id),HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Mono<ProductDto>> saveProduct(@RequestBody ProductDto productDto){
            return (productDto.getProdId()<=0 || Objects.isNull(productDto.getProdName()) || productDto.getPrice()<=0) ?
                    new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                    new ResponseEntity<>(productService.saveProduct(Mono.just(productDto)),HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Mono<ProductDto>> updateProduct(@PathVariable int id,@RequestBody ProductDto productDto){
          return (Objects.isNull(productDto.getProdName()) || productDto.getPrice()<=0) ?
               new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
         new ResponseEntity<>(productService.updateProduct(id,Mono.just(productDto)),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> deleteProduct(@PathVariable int id){
        return (Objects.isNull(id)) ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(productService.deleteProduct(id),HttpStatus.OK);
    }

    @GetMapping("/range")
    public ResponseEntity<Flux<ProductDto>> getProductsInRange(@RequestParam("min") double min,@RequestParam("max") double max){
        return (min<=0 || min>=max || max<=0 || max<=min ) ?
                new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                new ResponseEntity<>(productService.getProductInRange(min,max),HttpStatus.OK);
    }


}
