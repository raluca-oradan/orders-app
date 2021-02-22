package com.javaAdvanced.ordersapp.RESTAURANT.service;

import com.javaAdvanced.ordersapp.RESTAURANT.controller.RestaurantDTO;
import com.javaAdvanced.ordersapp.RESTAURANT.dao.RestaurantEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.dao.RestaurantRepository;
import com.javaAdvanced.ordersapp.USER.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantService {

        private RestaurantRepository restaurantRepository;

        @Autowired
        public RestaurantService(RestaurantRepository restaurantRepository){
            this.restaurantRepository  = restaurantRepository;
        }

        public List<RestaurantEntity> getAllRestaurants(){
            return restaurantRepository.findAll();
        }

        public RestaurantEntity getRestaurantById(long id) {
            if((!restaurantRepository.findById(id).isPresent())){
                throw new UserNotFoundException("User with id " + id + " not found!");
            }
            return restaurantRepository.findById(id).get();
        }

        public RestaurantEntity createRestaurant(RestaurantEntity restaurant)  {
            RestaurantEntity r = new RestaurantEntity();
            r.setName(restaurant.getName());
            r.setLocation(restaurant.getLocation());
            r.setDescription(restaurant.getDescription());
            return restaurantRepository.save(r);
        }

        public void updateRestaurant(long id, RestaurantDTO restaurantUpdated) {
            RestaurantEntity restaurant = getRestaurantById(id);
            restaurantRepository.update(id,restaurantUpdated.getName(),restaurantUpdated.getLocation(),
                                        restaurantUpdated.getDescription());
        }

        public void deleteRestaurant(long id) {
            RestaurantEntity restaurant = getRestaurantById(id);
            restaurantRepository.deleteById(id);
        }
    }
