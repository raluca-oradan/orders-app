package com.javaAdvanced.ordersapp.USER.model;

import com.javaAdvanced.ordersapp.USER.dao.Role;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDTO {
    private String email;
    private String password;
    private Role   role;

    @Autowired
    public UserDTO( String email, String password, Role role) {
        this.email    = email;
        this.password = password;
        this.role     = role;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
