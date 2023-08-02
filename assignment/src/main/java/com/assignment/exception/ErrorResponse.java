package com.assignment.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {

    private List<FieldError> fieldErrors;
    private List<ConstraintViolationError> violationErrors;
    private CustomErrors customErrors;
    private HttpStatus httpStatus;
    private String message;


    private ErrorResponse(List<FieldError> fieldErrors, List<ConstraintViolationError> violationErrors) {
        this.fieldErrors = fieldErrors;
        this.violationErrors = violationErrors;
    }

    private ErrorResponse(CustomErrors customErrors) {
        this.customErrors = customErrors;
    }

    private ErrorResponse(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    private ErrorResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public static ErrorResponse of(BindingResult bindingResult) {
        return new ErrorResponse(FieldError.of(bindingResult), null);
    }

    public static ErrorResponse of(Set<ConstraintViolation<?>> violations) {
        return new ErrorResponse(null, ConstraintViolationError.of(violations));
    }

    public static ErrorResponse of(ExceptionCode exceptionCode){
        return new ErrorResponse(CustomErrors.of(exceptionCode));
    }

    public static ErrorResponse of(AuthenticationException exceptionCode){
        return new ErrorResponse(CustomErrors.of(exceptionCode));
    }

    public static ErrorResponse of(HttpStatus httpStatus){
        return new ErrorResponse(httpStatus);
    }

    @Getter
    public static class FieldError{
        private String field;
        private Object rejectValue;
        private String reason;

        private FieldError(String field, Object rejectValue, String reason) {
            this.field = field;
            this.rejectValue = rejectValue;
            this.reason = reason;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();

            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()
                    )).collect(Collectors.toList());
        }
    }

    @Getter
    public static class ConstraintViolationError {
        private String propertyPath;
        private Object rejectedValue;
        private String reason;

        private ConstraintViolationError(String propertyPath, Object rejectedValue, String reason) {
            this.propertyPath = propertyPath;
            this.rejectedValue = rejectedValue;
            this.reason = reason;
        }

        public static List<ConstraintViolationError> of(Set<ConstraintViolation<?>> constraintViolations){
            return constraintViolations.stream()
                    .map(constraintViolation -> new ConstraintViolationError(
                            constraintViolation.getPropertyPath().toString(),
                            constraintViolation.getInvalidValue().toString(),
                            constraintViolation.getMessage()
                    )).collect(Collectors.toList());
        }
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

        public static CustomErrors of(AuthenticationException exceptionCode){

            CustomErrors customErrors = new CustomErrors(HttpStatus.UNAUTHORIZED, exceptionCode.getMessage());
            return customErrors;
        }
    }

    @Getter
    public static class AuthenticationCustomErrors{

        private HttpStatus httpStatus;
        private String message;

        private AuthenticationCustomErrors(HttpStatus httpStatus, String message) {
            this.httpStatus = httpStatus;
            this.message = message;
        }

        public static CustomErrors of(AuthenticationCustomErrors exceptionCode){

            CustomErrors customErrors = new CustomErrors(exceptionCode.getHttpStatus(), exceptionCode.getMessage());

            return customErrors;

        }
    }

}
