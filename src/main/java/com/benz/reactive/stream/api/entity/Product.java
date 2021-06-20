package com.benz.reactive.stream.api.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "product")
@Getter
@Setter
public class Product {

    @Id
    private int prodId;
    private String prodName;
    private double price;
}
