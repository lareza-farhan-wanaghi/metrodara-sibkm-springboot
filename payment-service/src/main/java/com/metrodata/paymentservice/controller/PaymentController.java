package com.metrodata.paymentservice.controller;

import com.metrodata.paymentservice.model.PaymentRequest;
import com.metrodata.paymentservice.model.PaymentResponse;
import com.metrodata.paymentservice.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/payment")
@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getAll(){
        return new ResponseEntity<>(paymentService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getById(@PathVariable long id){
        return new ResponseEntity<>(paymentService.getById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal')")
    @PostMapping
    public ResponseEntity<PaymentResponse> create(@RequestBody PaymentRequest paymentRequest){
        return new ResponseEntity<>(paymentService.create(paymentRequest), HttpStatus.CREATED);
    }
}
