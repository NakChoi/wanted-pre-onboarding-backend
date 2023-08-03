package com.assignment.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomErrorResponse {

    private CustomErrors customErrors;


    public CustomErrorResponse(CustomErrors customErrors) {
        this.customErrors = customErrors;
    }

    public static CustomErrorResponse of(ExceptionCode exceptionCode){
        return new CustomErrorResponse(CustomErrors.of(exceptionCode));
    }

    @Getter
    public static class CustomErrors{

        private HttpStatus httpStatus;
        private String message;

        private CustomErrors(HttpStatus httpStatus, String message) {
            this.httpStatus = httpStatus;
            this.message = message;
        }

        public static CustomErrors of(ExceptionCode exceptionCode){

            CustomErrors customErrors = new CustomErrors(exceptionCode.getHttpStatus(), exceptionCode.getMessage());

            return customErrors;

        }
    }
}


