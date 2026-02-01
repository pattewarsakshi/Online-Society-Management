package com.society.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class OwnerAlreadyExistsException extends RuntimeException {

    public OwnerAlreadyExistsException(String message) {
        super(message);
    }
}

