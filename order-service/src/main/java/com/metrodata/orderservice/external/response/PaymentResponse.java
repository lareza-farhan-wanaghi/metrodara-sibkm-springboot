package com.metrodata.orderservice.external.response;

import com.metrodata.orderservice.external.request.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private long id;
    private PaymentMode mode;
    private Instant date;
    private String status;
    private long amount;
    private long orderId;
}
