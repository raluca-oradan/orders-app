package com.javaAdvanced.ordersapp.EXCEPTIONS;

public class FoodItemNotFoundException extends RuntimeException{
    public FoodItemNotFoundException(String msg){
        super(msg);
    }
}
