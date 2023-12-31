package com.estore.controller;

import com.estore.dto.ProductDto;
import com.estore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productService;
    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/products")
    public ResponseEntity<String> store(@RequestBody ProductDto productDto){
        productService.store(productDto);

        return new ResponseEntity<>("Product added!", HttpStatus.CREATED);
    }
}
