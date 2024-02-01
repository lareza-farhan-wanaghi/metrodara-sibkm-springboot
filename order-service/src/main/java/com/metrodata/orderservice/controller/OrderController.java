package com.metrodata.orderservice.controller;

import com.metrodata.orderservice.entity.Order;
import com.metrodata.orderservice.model.OrderRequest;
import com.metrodata.orderservice.model.OrderResponse;
import com.metrodata.orderservice.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private OrderService orderService;

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll(){
        return new ResponseEntity(orderService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('Customer','Admin')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable("id") long orderId){
        return new ResponseEntity(orderService.getById(orderId),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('Customer','Admin')")
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest){
        log.info("Test");
        return new ResponseEntity(orderService.placeOrder(orderRequest), HttpStatus.CREATED);
    }

}
