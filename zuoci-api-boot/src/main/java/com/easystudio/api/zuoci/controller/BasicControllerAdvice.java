package com.easystudio.api.zuoci.controller;

import com.easystudio.api.zuoci.exception.ErrorException;
import com.easystudio.api.zuoci.model.error.Errors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static java.util.Collections.singletonList;

@ControllerAdvice(basePackages = "com.easystudio.api.zuoci")
public class BasicControllerAdvice {

    @ResponseBody
    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<Errors> handleException(ErrorException e) {
        return new ResponseEntity<>(new Errors(singletonList(e.getError())), e.getStatus());
    }
}
