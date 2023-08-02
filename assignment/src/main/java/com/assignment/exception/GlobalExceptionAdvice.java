package com.assignment.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse  handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        final ErrorResponse response = ErrorResponse.of(e);

        return response;

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {

        final ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());

        return response;
    }

    @ExceptionHandler
    public ResponseEntity handleCustomException(CustomException customException){
        log.error("handleCustomException : ", customException.getMessage());

        final CustomErrorResponse errorResponse = CustomErrorResponse.of(customException.getExceptionCode());

        return new ResponseEntity<>(errorResponse, customException.getExceptionCode().getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity handleAuthenticationCustomException(CustomAuthenticationException customException){
        log.error("handleCustomException : ", customException.getMessage());

        final CustomErrorResponse errorResponse = CustomErrorResponse.of(customException.getExceptionCode());

        return new ResponseEntity<>(errorResponse, customException.getExceptionCode().getHttpStatus());
    }
}



