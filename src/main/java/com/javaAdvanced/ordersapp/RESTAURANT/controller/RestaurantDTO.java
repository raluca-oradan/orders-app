package com.javaAdvanced.ordersapp.RESTAURANT.controller;

import com.javaAdvanced.ordersapp.USER.api.UserDTO;
import com.javaAdvanced.ordersapp.USER.dao.Role;
import org.springframework.beans.factory.annotation.Autowired;

public class RestaurantDTO {
    private String name;
    private String location;
    private String description;
    private String email;
    private String password;


    @Autowired
    public RestaurantDTO( String name, String location, String description, String email, String password) {
        this.name        = name;
        this.location    = location;
        this.description = description;
        this.email       = email;
        this.password    = password;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "RestaurantDTO{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
