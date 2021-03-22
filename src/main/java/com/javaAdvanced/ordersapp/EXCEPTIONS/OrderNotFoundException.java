package com.javaAdvanced.ordersapp.EXCEPTIONS;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(String msg){
        super(msg);
    }
}
