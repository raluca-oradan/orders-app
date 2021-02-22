package com.javaAdvanced.ordersapp.USER.model;

public class JWTmodel {

    private String token;

    public JWTmodel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
