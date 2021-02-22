package com.javaAdvanced.ordersapp.USER.exceptions;

public class InvalidPasswordException  extends RuntimeException{
    public InvalidPasswordException(String msg){
        super(msg);
    }
}
