package com.metrodata.productservice.service;

import com.metrodata.productservice.entity.Product;
import com.metrodata.productservice.model.ProductRequest;
import com.metrodata.productservice.model.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> getAll();
    ProductResponse getById(long id);
    ProductResponse create(ProductRequest productRequest);
    ProductResponse update(long id, ProductRequest productRequest);
    ProductResponse delete(long id);

    void checkAvailableProduct(long productId, int quantity);

void reduceQuantity(long productId, int quantity);

}
