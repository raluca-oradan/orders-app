package com.javaAdvanced.ordersapp.EXCEPTIONS;

public class FoodItemAlreadyExists extends RuntimeException{
    public FoodItemAlreadyExists(String msg){
        super(msg);
    }
}
