package com.metrodata.productservice.controller;

import com.metrodata.productservice.model.ProductRequest;
import com.metrodata.productservice.model.ProductResponse;
import com.metrodata.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller // HTML @ResponseBody JSON
@RestController // JSON
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll(){
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('Customer','SCOPE_internal','Admin')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable long id){
        return new ResponseEntity<>(productService.getById(id),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal')")
    @GetMapping("/checkAvailable/{id}")
    public ResponseEntity<Void> checkAvailableProduct(@PathVariable long id,
                                                      @RequestParam int quantity){
        productService.checkAvailableProduct(id,quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest productRequest){
        return new ResponseEntity<>(productService.create(productRequest), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable long id, @RequestBody ProductRequest productRequest){
        return new ResponseEntity<>(productService.update(id,productRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('SCOPE_internal')")
    @PutMapping("/reduceQuantity/{id}/{quantity}")
    public ResponseEntity<Void> reduceQuantity(@PathVariable long id,
                                                      @PathVariable int quantity){
        productService.reduceQuantity(id,quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponse> delete (@PathVariable long id){
        return new ResponseEntity<>(productService.delete(id),HttpStatus.OK);
    }

}
