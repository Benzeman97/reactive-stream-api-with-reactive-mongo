package com.benz.reactive.stream.api.exception;

import com.benz.reactive.stream.api.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DataNotFoundExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ErrorMessage> toResponse(DataNotFoundException ex){
        ErrorMessage errorMessage=new ErrorMessage(HttpStatus.NOT_FOUND.value(),ex.getMessage(),"www.benz.com");
        return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
    }
}
