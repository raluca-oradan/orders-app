package com.javaAdvanced.ordersapp.EMAIL.service;

import org.springframework.stereotype.Component;

@Component

public class EmailService {

public String sendEmail(String to, String from, String content){
    return content;
}
}
