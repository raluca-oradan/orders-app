package com.javaAdvanced.ordersapp.RESTAURANT.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javaAdvanced.ordersapp.USER.model.UserEntity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "restaurants")
@JsonAutoDetect

public class RestaurantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp
    private Timestamp created_at;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity userEntity;

    @OneToMany(mappedBy = "restaurantEntity",
              cascade = CascadeType.ALL,
              orphanRemoval = true,
              fetch = FetchType.LAZY)
    //@JsonIgnore
    private List<FoodCategoryEntity> foodCategoryEntityList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
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

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<FoodCategoryEntity> getFoodCategoryEntityList() {
        return foodCategoryEntityList;
    }

    public void setFoodCategoryEntityList(List<FoodCategoryEntity> foodCategoryEntityList) {
        this.foodCategoryEntityList = foodCategoryEntityList;
    }
}
