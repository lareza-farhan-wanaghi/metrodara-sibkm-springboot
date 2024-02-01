package com.metrodata.orderservice.model;

import com.metrodata.orderservice.external.request.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private int quantity;
    private long amount;
    private long productId;
    private PaymentMode mode;

}
