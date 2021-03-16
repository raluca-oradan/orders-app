package com.javaAdvanced.ordersapp.FOOD_ITEM.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.javaAdvanced.ordersapp.RESTAURANT.model.FoodCategoryEntity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "food_items")
@Entity
public class FoodItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreationTimestamp
    private Timestamp created_at;

    private String name;
    private String description;
    private double price;
    private double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_category_id")
    @JsonIgnore
    private FoodCategoryEntity foodCategoryEntity;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public FoodCategoryEntity getFoodCategoryEntity() {
        return foodCategoryEntity;
    }

    public void setFoodCategoryEntity(FoodCategoryEntity foodCategoryEntity) {
        this.foodCategoryEntity = foodCategoryEntity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
