package com.metrodata.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

    private long id;
    private int quantity;
    private long amount;
    private String status;
    private Instant date;
    private ProductResponse product;
}
