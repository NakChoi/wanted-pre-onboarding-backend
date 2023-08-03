package com.assignment.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member Not Found"),
    MEMBER_EXIST(HttpStatus.CONFLICT, "Member Exist"),
    MEMBER_NOT_MATCH(HttpStatus.UNAUTHORIZED, "Member Not Matched"),
    PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "Password is Not Matched"),
    ACCESSTOKEN_EXPIRATION(HttpStatus.UNAUTHORIZED, "AceessToken is Expired"),
    POST_EXISTS(HttpStatus.CONFLICT, "Post Exists"),
    EMAIL_FORMAT_INCORRECT(HttpStatus.UNAUTHORIZED, "Email format Incorrect"),
    PASSWORD_FORMAT_INCORRECT(HttpStatus.UNAUTHORIZED, "Password format Incorrect");


    @Getter
    private HttpStatus httpStatus;

    @Getter
    private String message;

    ExceptionCode(HttpStatus status, String message) {
        this.httpStatus = status;
        this.message = message;
    }
}



