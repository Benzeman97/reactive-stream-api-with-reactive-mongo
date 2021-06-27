package com.benz.reactive.stream.api.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ProductDto {

    private int prodId;
    private String prodName;
    private double price;
}
