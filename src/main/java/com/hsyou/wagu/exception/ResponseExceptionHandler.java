package com.hsyou.wagu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomNotFoundException.class)
    public final ResponseEntity<ErrorInfo> handleNotFoundException(CustomNotFoundException ex){
        ErrorInfo errorInfo = new ErrorInfo(ex.getMessage());
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }


}
