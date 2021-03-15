package com.javaAdvanced.ordersapp.RESTAURANT.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "food_categories")
public class FoodCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private Timestamp created_at;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @JsonIgnore
    private RestaurantEntity restaurantEntity;

    @OneToMany(mappedBy = "foodCategoryEntity",
               cascade  = CascadeType.ALL,
               orphanRemoval = true,
               fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<FoodItemEntity> foodItemEntityList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public RestaurantEntity getRestaurantEntity() {
        return restaurantEntity;
    }

    public void setRestaurantEntity(RestaurantEntity restaurantEntity) {
        this.restaurantEntity = restaurantEntity;
    }

    public List<FoodItemEntity> getFoodItemEntityList() {
        return foodItemEntityList;
    }

    public void setFoodItemEntityList(List<FoodItemEntity> foodItemEntityList) {
        this.foodItemEntityList = foodItemEntityList;
    }
}
