package com.javaAdvanced.ordersapp.EXCEPTIONS;

public class FoodCategoryNotFoundException extends RuntimeException{
    public FoodCategoryNotFoundException(String msg){
        super(msg);
    }
}
