package com.javaAdvanced.ordersapp.USER.exceptions;

public class UsedEmailException extends RuntimeException{
    public UsedEmailException(String msg){
        super(msg);
    }
}
