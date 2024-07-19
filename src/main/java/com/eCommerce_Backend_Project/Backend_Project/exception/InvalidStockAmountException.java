package com.eCommerce_Backend_Project.Backend_Project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidStockAmountException extends RuntimeException{
    public InvalidStockAmountException(Integer quantity){
        super("Invalid Stock Amount" + quantity);
    }
}
