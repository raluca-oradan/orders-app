package com.javaAdvanced.ordersapp.RESTAURANT.service;
import com.javaAdvanced.ordersapp.RESTAURANT.model.RestaurantDTO;
import com.javaAdvanced.ordersapp.RESTAURANT.model.RestaurantEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.dao.RestaurantRepository;
import com.javaAdvanced.ordersapp.EXCEPTIONS.UserNotFoundException;
import com.javaAdvanced.ordersapp.USER.dao.UserRepository;
import com.javaAdvanced.ordersapp.USER.model.UserEntity;
import com.javaAdvanced.ordersapp.USER.service.UserService;
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
                throw new UserNotFoundException("Restaurant with id " + id + " not found!");
            }
            return restaurantRepository.findById(id).get();
        }

        public RestaurantEntity createRestaurant(RestaurantDTO restaurant, UserEntity userEntity)  {
            RestaurantEntity r = new RestaurantEntity();
            r.setName(restaurant.getName());
            r.setLocation(restaurant.getLocation());
            r.setDescription(restaurant.getDescription());
            r.setUserEntity(userEntity);
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
