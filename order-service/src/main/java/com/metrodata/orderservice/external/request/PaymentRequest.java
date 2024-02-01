package com.metrodata.orderservice.external.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {

    private PaymentMode mode;
    private long amount;
    private long orderId;

}
