package com.javaAdvanced.ordersapp.USER.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.javaAdvanced.ordersapp.CUSTOMER.model.CustomerEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.model.RestaurantEntity;
import com.javaAdvanced.ordersapp.USER.dao.Role;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@JsonAutoDetect

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp
    private Timestamp created_at;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role",
            nullable = false)
    private Role role;

    @OneToOne(
        mappedBy      = "userEntity",
        cascade       = CascadeType.REMOVE,
        orphanRemoval = true,
        fetch         = FetchType.LAZY
    )
    private CustomerEntity customerEntity;

    @OneToOne(
            mappedBy      = "userEntity",
            cascade       = CascadeType.REMOVE,
            orphanRemoval = true,
            fetch         = FetchType.LAZY
    )
    private RestaurantEntity restaurantEntity;

    public UserEntity() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
