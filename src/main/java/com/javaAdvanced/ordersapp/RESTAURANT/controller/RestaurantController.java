package com.javaAdvanced.ordersapp.RESTAURANT.controller;

import com.javaAdvanced.ordersapp.RESTAURANT.dao.RestaurantEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.service.RestaurantService;
import com.javaAdvanced.ordersapp.USER.api.UserDTO;
import com.javaAdvanced.ordersapp.USER.dao.Role;
import com.javaAdvanced.ordersapp.USER.dao.UserEntity;
import com.javaAdvanced.ordersapp.USER.service.UserService;
import org.apache.catalina.User;
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

    @Autowired
    public RestaurantController(@Lazy UserService userService, @Lazy RestaurantService restaurantService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<List<RestaurantEntity>> getAllRestaurants(){
        return new ResponseEntity<List<RestaurantEntity>>(restaurantService.getAllRestaurants(), HttpStatus.OK);
    }

    @GetMapping ("{id}")
    public ResponseEntity <RestaurantEntity> getRestaurantById(@PathVariable int id)  {
        return new ResponseEntity<RestaurantEntity>(restaurantService.getRestaurantById(id), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RestaurantDTO restaurant)  {
        UserDTO user = new UserDTO(restaurant.getEmail(), restaurant.getPassword(), Role.RESTAURANT);
        userService.createUser(user);

        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setName(restaurant.getName());
        restaurantEntity.setLocation(restaurant.getLocation());
        restaurantEntity.setDescription(restaurant.getDescription());
        restaurantService.createRestaurant(restaurantEntity);
        return new ResponseEntity<>("Restaurant created! ", HttpStatus.CREATED);
    }


    /*
    @PutMapping("{id}")
    public ResponseEntity<String> updateRestaurant(@PathVariable int id,
                                                   @RequestBody UserDTO user,
                                                   @RequestBody RestaurantDTO restaurantrDTO)  {
        userService.updateUser(restaurantService.getRestaurantById(id).getUserEntity().getId(),user);
        restaurantService.updateRestaurant(id,restaurantrDTO);
        return new ResponseEntity<String>("Restaurant updated! ", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable int id)  {
        userService.deleteUser(restaurantService.getRestaurantById(id).getUserEntity().getId());
        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>("Restaurant deleted! ", HttpStatus.OK);
    }
    
     */
}

