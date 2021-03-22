package com.javaAdvanced.ordersapp.EXCEPTIONS;

import com.javaAdvanced.ordersapp.CUSTOMER.model.CustomerEntity;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String msg){
        super(msg);
    }
}
