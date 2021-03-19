package com.javaAdvanced.ordersapp.EXCEPTIONS;

public class ForbiddenAccesException extends RuntimeException{
    public ForbiddenAccesException(String msg){
        super(msg);
    }
}
