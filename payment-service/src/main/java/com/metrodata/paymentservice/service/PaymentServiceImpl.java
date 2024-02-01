package com.metrodata.paymentservice.service;

import com.metrodata.paymentservice.entity.Payment;
import com.metrodata.paymentservice.exception.CustomException;
import com.metrodata.paymentservice.model.PaymentMode;
import com.metrodata.paymentservice.model.PaymentRequest;
import com.metrodata.paymentservice.model.PaymentResponse;
import com.metrodata.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private PaymentRepository paymentRepository;

    @Override
    public List<PaymentResponse> getAll() {
        return paymentRepository.findAll()
                .stream()
                .map(payment -> {
                    return mappingPayemntToPaymentResponse(payment);
                })
                .collect(Collectors.toList());
    }

    @Override
    public PaymentResponse getById(long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Payment with given id" + id + " not found",
                        "PAYMENT_NOT_fOUND",
                        404));
        return mappingPayemntToPaymentResponse(payment);
    }

    @Override
    public PaymentResponse create(PaymentRequest paymentRequest) {
        Payment payment = Payment.builder()
                .date(Instant.now())
                .amount(paymentRequest.getAmount())
                .orderId(paymentRequest.getOrderId())
                .status("SUCCESS")
                .mode(paymentRequest.getMode().name())
                .build();

        paymentRepository.save(payment);
        return mappingPayemntToPaymentResponse(payment);
    }


    public PaymentResponse mappingPayemntToPaymentResponse(Payment payment){
        PaymentResponse paymentResponse = new PaymentResponse();
        BeanUtils.copyProperties(payment,paymentResponse);
        paymentResponse.setMode(PaymentMode.valueOf(payment.getMode()));
        return paymentResponse;
    }
}
