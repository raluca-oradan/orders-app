package com.javaAdvanced.ordersapp.RESTAURANT.service;
import com.javaAdvanced.ordersapp.EXCEPTIONS.ForbiddenAccesException;
import com.javaAdvanced.ordersapp.RESTAURANT.model.RestaurantDTO;
import com.javaAdvanced.ordersapp.RESTAURANT.model.RestaurantEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.dao.RestaurantRepository;
import com.javaAdvanced.ordersapp.EXCEPTIONS.UserNotFoundException;
import com.javaAdvanced.ordersapp.SECURITY.jwt.JWTprovider;
import com.javaAdvanced.ordersapp.USER.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantService {

    private RestaurantRepository restaurantRepository;
    private JWTprovider jwtProvider;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, JWTprovider jwtProvider) {
        this.restaurantRepository = restaurantRepository;
        this.jwtProvider = jwtProvider;
    }

    public List<RestaurantEntity> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public RestaurantEntity getRestaurantById(long id) {
        if ((!restaurantRepository.findById(id).isPresent())) {
            throw new UserNotFoundException("Restaurant with id " + id + " not found!");
        }
        return restaurantRepository.findById(id).get();
    }

    public RestaurantEntity createRestaurant(RestaurantDTO restaurant, UserEntity userEntity) {
        RestaurantEntity r = new RestaurantEntity();
        r.setName(restaurant.getName());
        r.setLocation(restaurant.getLocation());
        r.setDescription(restaurant.getDescription());
        r.setUserEntity(userEntity);
        return restaurantRepository.save(r);
    }

    public void updateRestaurant(long id, RestaurantEntity restaurantUpdated) {
        restaurantRepository.update(id, restaurantUpdated.getName(), restaurantUpdated.getLocation(),
                restaurantUpdated.getDescription());
    }

    public void checkRestaurantEmail(long id, String jwt) {
        UserEntity userEntity = getRestaurantById(id).getUserEntity();
        String email = jwtProvider.getSubjectFromJWT(jwt);
        if (!userEntity.getEmail().equals(email)) {
            throw new ForbiddenAccesException("You are not allowed to do this action!");
        }
    }
}
