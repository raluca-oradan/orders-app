package com.javaAdvanced.ordersapp.RESTAURANT.controller;

import com.javaAdvanced.ordersapp.EMAIL.EmailService;
import com.javaAdvanced.ordersapp.EXCEPTIONS.ForbiddenAccesException;
import com.javaAdvanced.ordersapp.RESTAURANT.model.FoodCategoryEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.model.RestaurantEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.model.RestaurantDTO;
import com.javaAdvanced.ordersapp.RESTAURANT.service.FoodCategoryService;
import com.javaAdvanced.ordersapp.RESTAURANT.service.RestaurantService;
import com.javaAdvanced.ordersapp.SECURITY.jwt.JWTprovider;
import com.javaAdvanced.ordersapp.USER.model.UserDTO;
import com.javaAdvanced.ordersapp.USER.dao.Role;
import com.javaAdvanced.ordersapp.USER.model.UserEntity;
import com.javaAdvanced.ordersapp.USER.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/restaurant")
public class RestaurantController {

    private UserService         userService;
    private RestaurantService   restaurantService;
    private EmailService        emailService;
    private FoodCategoryService foodCategoryService;

    @Autowired
    public RestaurantController(@Lazy UserService userService,
                                @Lazy RestaurantService restaurantService,
                                @Lazy EmailService emailService,
                                @Lazy FoodCategoryService foodCategoryService) {
        this.userService            = userService;
        this.restaurantService      = restaurantService;
        this.emailService           = emailService;
        this.foodCategoryService    = foodCategoryService;
    }


    //RESTAURANTS
    @GetMapping
    public ResponseEntity<List<RestaurantEntity>> getAllRestaurants(){
        return new ResponseEntity<List<RestaurantEntity>>(restaurantService.getAllRestaurants(), HttpStatus.OK);
    }

    @GetMapping ("{id}")
    public ResponseEntity <RestaurantEntity> getRestaurantById(@PathVariable int id)  {
        return new ResponseEntity<RestaurantEntity>(restaurantService.getRestaurantById(id), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RestaurantDTO restaurant)  {
        UserDTO user = new UserDTO(restaurant.getEmail(), restaurant.getPassword(),Role.RESTAURANT);
        UserEntity userEntity = userService.createUser(user);
        restaurantService.createRestaurant(restaurant,userEntity);
        emailService.send(restaurant.getEmail(),
                       "Dear " + restaurant.getName()+" welcome to our application!");
        return new ResponseEntity<>("Restaurant created! ", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('RESTAURANT')")
    public ResponseEntity<String> updateRestaurant(@PathVariable int id,
                                                   @RequestBody RestaurantEntity restaurant,
                                                   @RequestHeader("Authorization") String jwt)  {
        restaurantService.checkRestaurantEmail(id,jwt);
        restaurantService.updateRestaurant(id,restaurant);
        return new ResponseEntity<String>("Restaurant updated! ", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('RESTAURANT')")
    public ResponseEntity<String> deleteRestaurant(@PathVariable int id,
                                                   @RequestHeader("Authorization") String jwt)  {
        restaurantService.checkRestaurantEmail(id,jwt);
        userService.deleteUser(restaurantService.getRestaurantById(id).getUserEntity().getId());
        return new ResponseEntity<>("Restaurant deleted! ", HttpStatus.OK);
    }


    //FOOD_CATEGORIES
    @PostMapping("/{restaurantId}/foodCategory")
    @PreAuthorize("hasRole('RESTAURANT')")
    public ResponseEntity <FoodCategoryEntity> createFoodCategory(@PathVariable int restaurantId,
                                                                  @RequestBody FoodCategoryEntity foodCategoryEntity,
                                                                  @RequestHeader("Authorization") String jwt){
        restaurantService.checkRestaurantEmail(restaurantId,jwt);
        foodCategoryEntity.setRestaurantEntity(restaurantService.getRestaurantById(restaurantId));
        foodCategoryService.createFoodCategory(foodCategoryEntity,restaurantId);
        return ResponseEntity.ok(foodCategoryEntity);
    }

    @GetMapping("/{restaurantId}/foodCategory/{foodCategoryId}")
    public ResponseEntity <FoodCategoryEntity> getFoodCategoryById(@PathVariable int restaurantId,
                                                                   @PathVariable int foodCategoryId){
        restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(foodCategoryService.getFoodCategoryById(foodCategoryId));
    }

    @GetMapping("/{restaurantId}/foodCategory")
    public ResponseEntity <List<FoodCategoryEntity>> getAllFoodCategories(@PathVariable int restaurantId){
        restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(foodCategoryService.getAllFoodCategories(restaurantId));
    }

    @PutMapping("/{restaurantId}/foodCategory/{foodCategoryId}")
    @PreAuthorize("hasRole('RESTAURANT')")
    public ResponseEntity <String> updateFoodCategory(@PathVariable int restaurantId,
                                                      @RequestBody FoodCategoryEntity foodCategoryEntity,
                                                      @PathVariable int foodCategoryId,
                                                      @RequestHeader("Authorization") String jwt){
        restaurantService.checkRestaurantEmail(restaurantId,jwt);
        foodCategoryService.updateFoodCategory(foodCategoryId,foodCategoryEntity);
        return ResponseEntity.ok("Food category updated!");
    }

    @DeleteMapping("/{restaurantId}/foodCategory/{foodCategoryId}")
    @PreAuthorize("hasRole('RESTAURANT')")
    public ResponseEntity <String> deleteFoodCategory(@PathVariable int restaurantId,
                                                      @PathVariable int foodCategoryId,
                                                      @RequestHeader("Authorization") String jwt){
        restaurantService.checkRestaurantEmail(restaurantId,jwt);
        foodCategoryService.deleteFoodCategory(restaurantId,foodCategoryId);
        return ResponseEntity.ok("Food category deleted!");
    }
}

