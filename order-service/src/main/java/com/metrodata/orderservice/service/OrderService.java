package com.metrodata.orderservice.service;

import com.metrodata.orderservice.entity.Order;
import com.metrodata.orderservice.model.OrderRequest;
import com.metrodata.orderservice.model.OrderResponse;

import java.util.List;

public interface OrderService {

    List<OrderResponse> getAll();
    OrderResponse getById(long id);
    OrderResponse placeOrder(OrderRequest orderRequest);
    // Order Update
        // Success Payment or Failed Payment (Status)
    // Order Delete
}
