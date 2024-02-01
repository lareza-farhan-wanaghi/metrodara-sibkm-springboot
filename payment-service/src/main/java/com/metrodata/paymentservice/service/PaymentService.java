package com.metrodata.paymentservice.service;

import com.metrodata.paymentservice.model.PaymentRequest;
import com.metrodata.paymentservice.model.PaymentResponse;

import java.util.List;

public interface PaymentService {

    List<PaymentResponse> getAll();
    PaymentResponse getById(long id);
    PaymentResponse create(PaymentRequest paymentRequest);

}
