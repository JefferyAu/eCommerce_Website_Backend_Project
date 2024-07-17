package com.eCommerce_Backend_Project.Backend_Project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Integer id) {
        super("Product Not Found: "  + id);
    }
}
