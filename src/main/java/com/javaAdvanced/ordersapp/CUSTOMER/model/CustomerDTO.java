package com.javaAdvanced.ordersapp.CUSTOMER.model;

import org.springframework.beans.factory.annotation.Autowired;

public class CustomerDTO {
    private String name;
    private String phone_number;
    private String address;
    private String email;
    private String password;

    @Autowired
    public CustomerDTO(String name, String phone_number, String address, String email, String password) {
        this.name        = name;
        this.phone_number= phone_number;
        this.address     = address;
        this.email       = email;
        this.password    = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "name='" + name + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
