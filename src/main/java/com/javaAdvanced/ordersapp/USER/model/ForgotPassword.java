package com.javaAdvanced.ordersapp.USER.model;

public class ForgotPassword {
    private String email;

    public ForgotPassword(){
        super();
    }

    public ForgotPassword(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
