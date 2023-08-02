package com.assignment.exception;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;


@Getter
public class CustomAuthenticationException extends AuthenticationException {

    private ExceptionCode exceptionCode;

    public CustomAuthenticationException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
