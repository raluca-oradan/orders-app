package com.javaAdvanced.ordersapp.FOOD_ITEM.controller;

import com.javaAdvanced.ordersapp.FOOD_ITEM.model.FoodItemEntity;
import com.javaAdvanced.ordersapp.FOOD_ITEM.service.FoodItemService;
import com.javaAdvanced.ordersapp.RESTAURANT.model.FoodCategoryEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.service.FoodCategoryService;
import com.javaAdvanced.ordersapp.RESTAURANT.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("hasRole('RESTAURANT')")
@RequestMapping("/api/v1/foodCategory")
@RestController
public class FoodItemController {
    private FoodItemService foodItemService;
    private FoodCategoryService foodCategoryService;
    private RestaurantService restaurantService;

    @Autowired
    public FoodItemController(FoodItemService foodItemService,
                              FoodCategoryService foodCategoryService,
                              RestaurantService restaurantService){
        this.foodItemService     = foodItemService;
        this.foodCategoryService = foodCategoryService;
        this.restaurantService   = restaurantService;
    }


    //TODO limit this to restaurants only !!!!
    @PostMapping("/{foodCategoryId}/foodItem")
    public ResponseEntity<FoodItemEntity> createFoodItem(@PathVariable int foodCategoryId,
                                                         @RequestBody FoodItemEntity foodItemEntity,
                                                         @RequestHeader("Authorization") String jwt){
        restaurantService.checkRestaurantEmail(foodCategoryService.
                                               getFoodCategoryById(foodCategoryId).
                                               getRestaurantEntity().
                                               getId(),jwt);
        FoodCategoryEntity foodCategoryEntity = foodCategoryService.getFoodCategoryById(foodCategoryId);
        foodItemEntity.setFoodCategoryEntity(foodCategoryEntity);
        return ResponseEntity.ok(foodItemService.createFoodItem(foodCategoryId,foodItemEntity));
    }

    @GetMapping("/{foodCategoryId}/foodItem/{foodItemId}")
    public ResponseEntity <FoodItemEntity> getFoodItemById(@PathVariable int foodCategoryId,
                                                           @PathVariable int foodItemId){
        foodCategoryService.getFoodCategoryById(foodCategoryId);
        return ResponseEntity.ok(foodItemService.getFoodItemById(foodItemId));
    }

    @GetMapping("/{foodCategoryId}/foodItem")
    public ResponseEntity <List<FoodItemEntity>> getAllFoodItems(@PathVariable int foodCategoryId){
        foodCategoryService.getFoodCategoryById(foodCategoryId);
        return ResponseEntity.ok(foodItemService.getAllFoodItems(foodCategoryId));
    }

    @PutMapping("/{foodCategoryId}/foodItem/{foodItemId}")
    public ResponseEntity <String> updateFoodItem(@PathVariable int foodCategoryId,
                                                  @RequestBody FoodItemEntity foodItemEntity,
                                                  @PathVariable int foodItemId,
                                                  @RequestHeader("Authorization") String jwt){
        restaurantService.checkRestaurantEmail(foodCategoryService.
                getFoodCategoryById(foodCategoryId).
                getRestaurantEntity().
                getId(),jwt);
        foodCategoryService.getFoodCategoryById(foodCategoryId);
        foodItemService.updateFoodItem(foodItemId,foodItemEntity);
        return ResponseEntity.ok("Food item updated!");
    }

    @DeleteMapping("/{foodCategoryId}/foodItem/{foodItemId}")
    public ResponseEntity <String> deleteFoodItem(@PathVariable int foodCategoryId,
                                                  @PathVariable int foodItemId,
                                                  @RequestHeader("Authorization") String jwt){
        restaurantService.checkRestaurantEmail(foodCategoryService.
                getFoodCategoryById(foodCategoryId).
                getRestaurantEntity().
                getId(),jwt);
        foodCategoryService.getFoodCategoryById(foodCategoryId);
        foodItemService.deleteFoodItem(foodCategoryId,foodItemId);
        return ResponseEntity.ok("Food item deleted!");
    }
}
