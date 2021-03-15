package com.javaAdvanced.ordersapp.RESTAURANT.controller;

import com.javaAdvanced.ordersapp.EMAIL.EmailService;
import com.javaAdvanced.ordersapp.RESTAURANT.model.FoodCategoryEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.model.FoodItemEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.model.RestaurantEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.model.RestaurantDTO;
import com.javaAdvanced.ordersapp.RESTAURANT.service.FoodCategoryService;
import com.javaAdvanced.ordersapp.RESTAURANT.service.FoodItemService;
import com.javaAdvanced.ordersapp.RESTAURANT.service.RestaurantService;
import com.javaAdvanced.ordersapp.USER.model.UserDTO;
import com.javaAdvanced.ordersapp.USER.dao.Role;
import com.javaAdvanced.ordersapp.USER.model.UserEntity;
import com.javaAdvanced.ordersapp.USER.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/restaurant")
public class RestaurantController {

    private UserService userService;
    private RestaurantService restaurantService;
    private EmailService emailService;
    private FoodCategoryService foodCategoryService;
    private FoodItemService foodItemService;

    @Autowired
    public RestaurantController(@Lazy UserService userService,
                                @Lazy RestaurantService restaurantService,
                                @Lazy EmailService emailService,
                                @Lazy FoodCategoryService foodCategoryService,
                                @Lazy FoodItemService foodItemService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.emailService = emailService;
        this.foodCategoryService = foodCategoryService;
        this.foodItemService = foodItemService;
    }


    //RESTAURANT
    @GetMapping
    public ResponseEntity<List<RestaurantEntity>> getAllRestaurants() {
        return new ResponseEntity<List<RestaurantEntity>>(restaurantService.getAllRestaurants(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<RestaurantEntity> getRestaurantById(@PathVariable int id) {
        return new ResponseEntity<RestaurantEntity>(restaurantService.getRestaurantById(id), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RestaurantDTO restaurant) {
        UserDTO user = new UserDTO(restaurant.getEmail(), restaurant.getPassword(), Role.RESTAURANT);
        UserEntity userEntity = userService.createUser(user);
        restaurantService.createRestaurant(restaurant, userEntity);
        emailService.send(restaurant.getEmail(),
                "Dear " + restaurant.getName() + " welcome to our application!");
        return new ResponseEntity<>("Restaurant created! ", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateRestaurant(@PathVariable int id,
                                                   @RequestBody RestaurantDTO restaurant) {
        UserDTO user = new UserDTO(restaurant.getEmail(), restaurant.getPassword(), Role.RESTAURANT);
        userService.updateUser(restaurantService.getRestaurantById(id).getUserEntity().getId(), user);
        restaurantService.updateRestaurant(id, restaurant);
        return new ResponseEntity<String>("Restaurant updated! ", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable int id) {
        userService.deleteUser(restaurantService.getRestaurantById(id).getUserEntity().getId());
        return new ResponseEntity<>("Restaurant deleted! ", HttpStatus.OK);
    }

    //FOOD_CATEGORY
    @PostMapping("/{restaurantId}/foodCategories")
    public ResponseEntity<FoodCategoryEntity> createFoodCategory(@PathVariable int restaurantId,
                                                                 @RequestBody FoodCategoryEntity foodCategoryEntity) {
        RestaurantEntity restaurant = restaurantService.getRestaurantById(restaurantId);
        foodCategoryEntity.setRestaurantEntity(restaurant);
        foodCategoryService.createFoodCategory(foodCategoryEntity, restaurantId);
        return ResponseEntity.ok(foodCategoryEntity);
    }

    @GetMapping("/{restaurantId}/foodCategories/{foodCategoryId}")
    public ResponseEntity<FoodCategoryEntity> getFoodCategoryById(@PathVariable int restaurantId,
                                                                  @PathVariable int foodCategoryId) {
        restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(foodCategoryService.getFoodCategoryById(foodCategoryId));
    }

    @GetMapping("/{restaurantId}/foodCategories")
    public ResponseEntity<List<FoodCategoryEntity>> getAllFoodCategories(@PathVariable int restaurantId) {
        restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(foodCategoryService.getAllFoodCategories(restaurantId));
    }

    @PutMapping("/{restaurantId}/foodCategories/{foodCategoryId}")
    public ResponseEntity<String> updateFoodCategory(@PathVariable int restaurantId,
                                                     @RequestBody FoodCategoryEntity foodCategoryEntity,
                                                     @PathVariable int foodCategoryId) {
        restaurantService.getRestaurantById(restaurantId);
        foodCategoryService.updateFoodCategory(foodCategoryId, foodCategoryEntity);
        return ResponseEntity.ok("Food category updated!");
    }

    @DeleteMapping("/{restaurantId}/foodCategories/{foodCategoryId}")
    public ResponseEntity<String> deleteFoodCategory(@PathVariable int restaurantId,
                                                     @PathVariable int foodCategoryId) {
        restaurantService.getRestaurantById(restaurantId);
        foodCategoryService.deleteFoodCategory(restaurantId, foodCategoryId);
        return ResponseEntity.ok("Food category deleted!");
    }


    //FOOD_ITEM
    @PostMapping("/{restaurantId}/foodCategories/{foodCategoryId}/foodItems")
    public ResponseEntity<FoodItemEntity> createFoodItem(@PathVariable int restaurantId,
                                                         @PathVariable int foodCategoryId,
                                                         @RequestBody FoodItemEntity foodItemEntity) {
        restaurantService.getRestaurantById(restaurantId);
        FoodCategoryEntity foodCategoryEntity = foodCategoryService.getFoodCategoryById(foodCategoryId);
        foodItemEntity.setFoodCategoryEntity(foodCategoryEntity);
        foodItemService.createFoodItem(foodItemEntity, foodCategoryId);
        return ResponseEntity.ok(foodItemEntity);
    }

    @GetMapping("/{restaurantId}/foodCategories/{foodCategoryId}/foodItems/{foodItemId}")
    public ResponseEntity<FoodItemEntity> getFoodItemById(@PathVariable int restaurantId,
                                                          @PathVariable int foodCategoryId,
                                                          @PathVariable int foodItemId) {
        restaurantService.getRestaurantById(restaurantId);
        foodCategoryService.getFoodCategoryById(foodCategoryId);
        return ResponseEntity.ok(foodItemService.getFoodItemById(foodItemId));
    }

    @GetMapping("/{restaurantId}/foodCategories/{foodCategoryId}/foodItems")
    public ResponseEntity<List<FoodItemEntity>> getAllFoodItems(@PathVariable int restaurantId,
                                                                @PathVariable int foodCategoryId) {
        restaurantService.getRestaurantById(restaurantId);
        foodCategoryService.getFoodCategoryById(foodCategoryId);
        return ResponseEntity.ok(foodItemService.getAllFoodItems(foodCategoryId));
    }

    @PutMapping("/{restaurantId}/foodCategories/{foodCategoryId}/foodItems/{foodItemId}")
    public ResponseEntity<String> updateFoodItem(@PathVariable int restaurantId,
                                                 @PathVariable int foodCategoryId,
                                                 @PathVariable int foodItemId,
                                                 @RequestBody FoodItemEntity foodItemEntity) {
        restaurantService.getRestaurantById(restaurantId);
        foodCategoryService.getFoodCategoryById(foodCategoryId);
        foodItemService.updateFoodItem(foodItemId, foodItemEntity);
        return ResponseEntity.ok("Food item updated!");
    }

    @DeleteMapping("/{restaurantId}/foodCategories/{foodCategoryId}/foodItems/{foodItemId}")
    public ResponseEntity<String> deleteFoodItem(@PathVariable int restaurantId,
                                                 @PathVariable int foodCategoryId,
                                                 @PathVariable int foodItemId) {
        restaurantService.getRestaurantById(restaurantId);
        foodCategoryService.getFoodCategoryById(foodCategoryId);
        foodItemService.deleteFoodItem(foodCategoryId,foodItemId);
        return ResponseEntity.ok("Food item deleted!");
    }
}


