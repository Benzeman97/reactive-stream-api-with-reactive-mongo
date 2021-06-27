package com.benz.reactive.stream.api.service.impl;


import com.benz.reactive.stream.api.dao.ProductDao;
import com.benz.reactive.stream.api.dto.ProductDto;
import com.benz.reactive.stream.api.entity.Product;
import com.benz.reactive.stream.api.service.ProductService;
import com.benz.reactive.stream.api.utils.ProductUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@DisplayName("ProductServiceImplTest")
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductDao productDao;

    @Test
    @DisplayName("getProductsTest")
    public void getProductsTest(){
         Flux<ProductDto> expectedProducts = Flux.just(product_1(),product_2());

      Mockito.when(productDao.findAll(Sort.by("prodId").descending())).thenReturn(
              expectedProducts.map(prod->ProductUtils.dtoToEntity(prod)));

        Flux<ProductDto> actualProducts = productService.getProducts();

        StepVerifier.create(actualProducts)
                .expectSubscription()
                 .expectNext(product_1())
                .expectNext(product_2())
                .verifyComplete();
    }

    @Test
    @DisplayName("findProductTest")
    public void findProductTest(){

        Mono<ProductDto> expectedProduct = Mono.just(product_2());

        Mockito.when(productDao.findById(product_2().getProdId())).thenReturn(
                expectedProduct.map(prod->ProductUtils.dtoToEntity(prod)));

        Mono<ProductDto> actualProduct = productService.findProduct(product_2().getProdId());

        StepVerifier.create(actualProduct)
                .expectSubscription()
                .expectNextMatches(prod->prod.getProdId()==product_2().getProdId())
                .verifyComplete();
    }

    @Test
    @DisplayName("saveProductTest")
    public void saveProductTest(){

        Product product=new Product();
         BeanUtils.copyProperties(product_1(),product);

         Mono<ProductDto> expectedProduct = Mono.just(product_1());

         Mockito.when(productDao.save(product)).thenReturn(
                 expectedProduct.map(prod->ProductUtils.dtoToEntity(prod)));

         productService.saveProduct(expectedProduct);

    }

    @Test
    @DisplayName("updateProductTest")
    public void updateProductTest(){

        ProductDto product = new ProductDto();
        BeanUtils.copyProperties(product_1(),product);
        product.setPrice(200000.0);

        Product updatedProduct=new Product();
        BeanUtils.copyProperties(product,updatedProduct);

        Mono<ProductDto> expectedProduct = Mono.just(product);

        Mockito.when(productDao.findById(product.getProdId())).thenReturn(
                expectedProduct.map(prod->ProductUtils.dtoToEntity(prod)));

          Mockito.when(productDao.save(updatedProduct)).thenReturn(
                  expectedProduct.map(prod->ProductUtils.dtoToEntity(prod)));

       productService.updateProduct(product.getProdId(),expectedProduct);

    }

    @Test
    @DisplayName("deleteProductTest")
    public void deleteProductTest(){
        int prodId=101;

        Mono<ProductDto> expectedProduct = Mono.just(product_1());

       Mockito.when(productDao.findById(prodId)).thenReturn(
               expectedProduct.map(prod->ProductUtils.dtoToEntity(prod)));

        productService.deleteProduct(prodId);

        productDao.findById(prodId)
                .map(prod-> Mockito.verify(productDao,Mockito.times(1)).delete(prod));
    }

    @Test
    @DisplayName("getProductInRangeTest")
    public void getProductInRangeTest(){
         double min=100000.0;
         double max=150000.0;

         Flux<ProductDto> expectedProducts= Flux.just(product_1());

         Mockito.when(productDao.findByPriceBetween(Range.closed(min,max)))
                 .thenReturn(expectedProducts.map(prod->ProductUtils.dtoToEntity(prod)));

        Flux<ProductDto> actualProducts = productService.getProductInRange(min,max);

        StepVerifier.create(actualProducts)
                .expectSubscription()
                .expectNext(product_1())
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
