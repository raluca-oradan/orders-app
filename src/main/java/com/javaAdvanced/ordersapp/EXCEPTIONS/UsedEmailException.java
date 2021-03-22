package com.javaAdvanced.ordersapp.EXCEPTIONS;

public class UsedEmailException extends RuntimeException{
    public UsedEmailException(String msg){
        super(msg);
    }
}
