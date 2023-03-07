package com.example.productcategoryservice.exception;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException(Error error) {
        super(error);
    }
}


