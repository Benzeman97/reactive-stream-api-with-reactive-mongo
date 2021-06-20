package com.benz.reactive.stream.api.utils;

import com.benz.reactive.stream.api.dto.ProductDto;
import com.benz.reactive.stream.api.entity.Product;
import org.springframework.beans.BeanUtils;

public class ProductUtils {

    public static ProductDto entityToDto(Product product){
         ProductDto productDto=new ProductDto();
        BeanUtils.copyProperties(product,productDto);
        return productDto;
    }

    public static Product dtoToEntity(ProductDto productDto){
        Product product=new Product();
        BeanUtils.copyProperties(productDto,product);
        return product;
    }
}
