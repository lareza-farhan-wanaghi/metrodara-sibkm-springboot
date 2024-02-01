package com.metrodata.orderservice.service;

import com.metrodata.orderservice.entity.Order;
import com.metrodata.orderservice.exception.CustomException;
import com.metrodata.orderservice.external.request.PaymentRequest;
import com.metrodata.orderservice.external.response.PaymentResponse;
import com.metrodata.orderservice.model.OrderRequest;
import com.metrodata.orderservice.model.OrderResponse;
import com.metrodata.orderservice.model.ProductResponse;
import com.metrodata.orderservice.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private RestTemplate restTemplate;

    @Override
    public List<OrderResponse> getAll() {
        List<Order> orders = orderRepository.findAll();
        
        List<OrderResponse> orderResponses = orders.stream().map(order -> {
            OrderResponse orderResponse = mappingOrderToOrderResponse(order);
            
            // Make a request to the product service to fetch product data
            ResponseEntity<ProductResponse> response = restTemplate.exchange(
                System.getenv("PRODUCT_SERVICE_URL") + "/product/" + order.getProductId(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ProductResponse>() {}
            );
            
            ProductResponse productResponse = response.getBody();
            
            // Set the product data in the OrderResponse
            orderResponse.setProduct(productResponse);
            
            return orderResponse;
        }).collect(Collectors.toList());
        
        return orderResponses;
    }


    @Override
    public OrderResponse getById(long id) {
        ProductResponse prodResp;
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Order with given id " + id + " Not Found",
                        "ORDER_NOT_FOUND",
                        404));
        // make request to product service
        ResponseEntity<ProductResponse> response = restTemplate.exchange(
                System.getenv("PRODUCT_SERVICE_URL")+"/product/" + order.getProductId(),
                HttpMethod.GET,
                null, // Request Entity (HTTP HEADERS, Request Body)
                new ParameterizedTypeReference<ProductResponse>() {
                });
        prodResp = response.getBody();
        OrderResponse orderResponse = mappingOrderToOrderResponse(order);
        orderResponse.setProduct(prodResp);
        return orderResponse;
    }

    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        // Product Service
        // Check Available Product => Quantity >= Product Quantity
        ResponseEntity<Void> checkAvailabilityResponse = restTemplate.exchange(
                System.getenv("PRODUCT_SERVICE_URL")+"/product/checkAvailable/" + orderRequest.getProductId() + "?quantity=" + orderRequest.getQuantity(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Void>() {
                }
        );

        // PlaceOrder
        Order order = Order.builder()
                .amount(orderRequest.getAmount())
                .quantity(orderRequest.getQuantity())
                .productId(orderRequest.getProductId())
                .status("CREATED")
                .date(Instant.now())
                .build();

        Order res = orderRepository.save(order);

        // Payment Service -> Create Payment
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .mode(orderRequest.getMode())
                .orderId(res.getId())
                .amount(order.getAmount())
                .build();

        String orderStatus;
        OrderResponse orderResponse = mappingOrderToOrderResponse(res);

        try {
            ResponseEntity<PaymentResponse> paymentResponse = restTemplate.exchange(
                    System.getenv("PAYMENT_SERVICE_URL")+"/payment",
                    HttpMethod.POST,
                    new HttpEntity<>(paymentRequest),
                    new ParameterizedTypeReference<PaymentResponse>() {
                    }
            );

            // Fetch product data
            ResponseEntity<ProductResponse> productResponse = restTemplate.exchange(
                    System.getenv("PRODUCT_SERVICE_URL")+"/product/" + orderRequest.getProductId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ProductResponse>() {
                    }
            );

            orderStatus = "PLACED";
            // ReduceQuantity -> Success
            restTemplate.exchange(
                    System.getenv("PRODUCT_SERVICE_URL")+"/product/reduceQuantity/" + orderRequest.getProductId() + "/" + orderRequest.getQuantity(),
                    HttpMethod.PUT,
                    null,
                    new ParameterizedTypeReference<Void>() {
                    }
            );

            orderResponse.setProduct(productResponse.getBody());
            

        } catch (Exception e) {
            orderStatus = "PAYMENT_FAILED";
        }

        // Failed Payment
        order.setStatus(orderStatus);
        orderRepository.save(order);

        return orderResponse;
    }

    public OrderResponse mappingOrderToOrderResponse(Order order){
        OrderResponse orderResponse = new OrderResponse();
        BeanUtils.copyProperties(order,orderResponse);
        return orderResponse;
    }
}
