package com.federicobonel.mapcoloring.controllers;

import com.federicobonel.mapcoloring.exceptions.InvalidQueryFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {InvalidQueryFormat.class})
    public ResponseEntity<Object> handleInvalidQueries() {
        return new ResponseEntity<>("Invalid query", new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
