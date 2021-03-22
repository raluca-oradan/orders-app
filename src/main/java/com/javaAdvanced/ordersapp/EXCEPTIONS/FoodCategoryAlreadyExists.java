package com.javaAdvanced.ordersapp.EXCEPTIONS;

import com.javaAdvanced.ordersapp.RESTAURANT.model.FoodCategoryEntity;

public class FoodCategoryAlreadyExists extends RuntimeException{
    public FoodCategoryAlreadyExists(String msg){
        super(msg);
    }
}
