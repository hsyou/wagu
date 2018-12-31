package com.hsyou.wagu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not Found")
public class CustomNotFoundException extends RuntimeException{

    public CustomNotFoundException(String message) {
        super(message);
    }
}
