package com.javaAdvanced.ordersapp.RESTAURANT.controller;

import com.javaAdvanced.ordersapp.EMAIL.EmailService;
import com.javaAdvanced.ordersapp.RESTAURANT.model.RestaurantEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.model.RestaurantDTO;
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

    @Autowired
    public RestaurantController(@Lazy UserService userService,
                                @Lazy RestaurantService restaurantService,
                                @Lazy EmailService emailService) {
        this.userService = userService;
        this.restaurantService = restaurantService;
        this.emailService = emailService;
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
        UserDTO user = new UserDTO(restaurant.getEmail(), restaurant.getPassword(),Role.RESTAURANT);
        UserEntity userEntity = userService.createUser(user);
        restaurantService.createRestaurant(restaurant,userEntity);
        emailService.send(restaurant.getEmail(),
                       "Dear" + restaurant.getName()+" welcome to our application!");
        return new ResponseEntity<>("Restaurant created! ", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateRestaurant(@PathVariable int id,
                                                   @RequestBody RestaurantDTO restaurant)  {
        UserDTO user = new UserDTO(restaurant.getEmail(), restaurant.getPassword(),Role.RESTAURANT);
        userService.updateUser(restaurantService.getRestaurantById(id).getUserEntity().getId(),user);
        restaurantService.updateRestaurant(id,restaurant);
        return new ResponseEntity<String>("Restaurant updated! ", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable int id)  {
        userService.deleteUser(restaurantService.getRestaurantById(id).getUserEntity().getId());
        return new ResponseEntity<>("Restaurant deleted! ", HttpStatus.OK);
    }
}

