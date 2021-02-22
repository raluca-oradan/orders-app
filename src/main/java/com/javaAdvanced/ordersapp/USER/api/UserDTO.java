package com.javaAdvanced.ordersapp.USER.api;

import com.javaAdvanced.ordersapp.USER.dao.Role;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDTO {
    private long id;
    private String email;
    private String password;
    private Role role;

    @Autowired
    public UserDTO(long id, String email, String password, Role role) {
        this.id       = id;
        this.email    = email;
        this.password = password;
        this.role     = role;
    }

    public long getId() {
        return id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setId(long id) {
        this.id = id;
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
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
