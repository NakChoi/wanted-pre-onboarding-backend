package com.assignment.exception;


import lombok.Getter;

@Getter
public class CustomException  extends RuntimeException{
    @Getter
    private ExceptionCode exceptionCode;

    public CustomException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}


