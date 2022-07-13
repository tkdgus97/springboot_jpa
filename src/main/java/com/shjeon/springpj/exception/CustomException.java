package com.shjeon.springpj.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class CustomException {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public String IllegalExceptionHandler(IllegalArgumentException e){
        return e.getMessage();
    }
}
