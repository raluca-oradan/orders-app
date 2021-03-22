package com.javaAdvanced.ordersapp.EXCEPTIONS;

public class InvalidPasswordException  extends RuntimeException{
    public InvalidPasswordException(String msg){
        super(msg);
    }
}
