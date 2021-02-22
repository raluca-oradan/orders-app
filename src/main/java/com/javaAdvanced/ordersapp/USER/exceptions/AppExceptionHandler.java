package com.javaAdvanced.ordersapp.USER.exceptions;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order
@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFoundException(UserNotFoundException e, WebRequest request){
        ApiError apiError1 = new ApiError(404, e.getMessage(), request.getContextPath());
        return new ResponseEntity<>(apiError1, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiError> handleInvalidPasswordException(InvalidPasswordException e, WebRequest request){
        ApiError apiError2 = new ApiError(406, e.getMessage(), request.getContextPath());
        return new ResponseEntity<>(apiError2, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UsedEmailException.class)
    public ResponseEntity<ApiError> handleUsedEmailException(UsedEmailException e, WebRequest request){
        ApiError apiError3 = new ApiError(406, e.getMessage(), request.getContextPath());
        return new ResponseEntity<>(apiError3, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception e, WebRequest request){
        ApiError apiError = new ApiError(500, e.getMessage(), request.getContextPath());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

