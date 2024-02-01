package com.metrodata.productservice.service;

import com.metrodata.productservice.entity.Product;
import com.metrodata.productservice.exception.CustomException;
import com.metrodata.productservice.model.ProductRequest;
import com.metrodata.productservice.model.ProductResponse;
import com.metrodata.productservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanMetadataAttribute;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> {
                    return mappingProductToProductResponses(product);
                }).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        "Product with given id " + id + " not found",
                        "ORDER_NOT_FOUND",
                        404
                ));
        return mappingProductToProductResponses(product);
    }

    @Override
    public void checkAvailableProduct(long productId, int quantity) {
        ProductResponse product = getById(productId);
        if (product.getQuantity() < quantity){
            throw new CustomException(
                    "Product does not have sufficient Quantity",
                    "PRODUCT_INSUFFICIENT_QUANTITY",
                    400
            );
        }
    }

    @Override
    public void reduceQuantity(long productId, int quantity) {
        ProductResponse productResponse = getById(productId);
        Product product = new Product();
        BeanUtils.copyProperties(productResponse, product);

        product.setQuantity(product.getQuantity()-quantity);
        productRepository.save(product);
    }

    @Override
    public ProductResponse create(ProductRequest productRequest) {
        Product product = mappingProductRequestToProduct(productRequest);
        Product res = productRepository.save(product);
        return mappingProductToProductResponses(res);
    }

    @Override
    public ProductResponse update(long id, ProductRequest productRequest) {
        getById(id);
        Product product = mappingProductRequestToProduct(productRequest);
        Product res = productRepository.save(product);
        return mappingProductToProductResponses(res);
    }

    @Override
    public ProductResponse delete(long id) {
        ProductResponse productResponse = getById(id);
        productRepository.deleteById(id);
        return productResponse;
    }



    private ProductResponse mappingProductToProductResponses(Product product) {
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(product, productResponse);
        return productResponse;
    }

    private Product mappingProductRequestToProduct(ProductRequest productRequest) {
        Product product = new Product();
        BeanUtils.copyProperties(productRequest, product);
        return product;
    }
}
