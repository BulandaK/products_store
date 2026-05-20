package com.example.products.exception;

import org.springframework.http.HttpStatus;

public class ProductAlreadyExistsException extends ProductException{
    public ProductAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
