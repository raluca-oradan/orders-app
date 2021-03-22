package com.javaAdvanced.ordersapp.EXCEPTIONS;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String msg){
        super(msg);
    }
}
