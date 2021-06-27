package com.benz.reactive.stream.api.controller;

import com.benz.reactive.stream.api.dto.ProductDto;
import com.benz.reactive.stream.api.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@WebFluxTest(ProductController.class)
@ExtendWith({SpringExtension.class})
@DisplayName("ProductControllerTest")
public class ProductControllerTest{

    @MockBean
    private ProductService productService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("getProductsTest")
    public void getProductsTest(){

        Flux<ProductDto> expectedProducts = Flux.just(product_2(),product_1());
        Mockito.when(productService.getProducts()).thenReturn(expectedProducts);

        Flux<ProductDto> actualProducts = webTestClient.get().uri("/api/product")
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();

        StepVerifier.create(actualProducts)
                .expectSubscription()
                .expectNext(product_2())
                .expectNext(product_1())
                .verifyComplete();

    }

    @Test
    @DisplayName("findProductTest")
    public void findProductTest(){

        Mono<ProductDto> expectedProduct = Mono.just(product_2());
        Mockito.when(productService.findProduct(product_2().getProdId())).thenReturn(expectedProduct);

        Flux<ProductDto> actualProduct = webTestClient.get().uri("/api/product/{id}",product_2().getProdId())
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();

        StepVerifier.create(actualProduct)
                .expectSubscription()
                .expectNextMatches(prod->prod.getProdId()==102)
                .verifyComplete();

    }

    @Test
    @DisplayName("saveProductTest")
    public void saveProductTest(){

        Mono<ProductDto> expectedProduct=Mono.just(product_1());
        Mockito.when(productService.saveProduct(expectedProduct)).thenReturn(expectedProduct);

      webTestClient.post().uri("/api/product/save")
                  .contentType(MediaType.APPLICATION_JSON)
                  .body(expectedProduct,ProductDto.class)
                  .exchange()
                 .expectStatus().isCreated();
    }

    @Test
    @DisplayName("updateProductTest")
    public void updateProduct(){
         ProductDto updatedProduct=product_2();
         updatedProduct.setPrice(100000.0);

         Mono<ProductDto> expectedProduct = Mono.just(updatedProduct);
         Mockito.when(productService.updateProduct(updatedProduct.getProdId(),expectedProduct))
                 .thenReturn(expectedProduct);

         webTestClient.put().uri("/api/product/update/{id}",updatedProduct.getProdId())
                 .contentType(MediaType.APPLICATION_JSON)
                 .body(expectedProduct,ProductDto.class)
                 .exchange()
                 .expectStatus().isCreated();
    }

    @Test
    @DisplayName("deleteProductTest")
    public void deleteProductTest(){
        int prodId=102;

        Mockito.when(productService.deleteProduct(prodId))
                .thenReturn(Mono.empty());

        webTestClient.delete().uri("/api/product/{id}",prodId)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DisplayName("getProductsInRangeTest")
    public void getProductsInRangeTest(){

        double min=30000.0;
        double max=100000.0;

        Flux<ProductDto> expectedProducts = Flux.just(product_2());

        Mockito.when(productService.getProductInRange(min,max)).thenReturn(expectedProducts);

        Flux<ProductDto> actualProducts = webTestClient.get().uri("/api/product/range?min={min}&max={max}",min,max)
                .exchange()
                .expectStatus().isOk()
                .returnResult(ProductDto.class)
                .getResponseBody();

        StepVerifier.create(actualProducts)
                .expectSubscription()
                .expectNext(product_2())
                .verifyComplete();

    }

    private ProductDto product_1(){
        ProductDto product=new ProductDto();
        product.setProdId(101);
        product.setProdName("Laptop");
        product.setPrice(145000.0);
        return product;
    }

    private ProductDto product_2(){
        ProductDto product=new ProductDto();
        product.setProdId(102);
        product.setProdName("Mobile Phone");
        product.setPrice(75000.0);
        return product;
    }
}
