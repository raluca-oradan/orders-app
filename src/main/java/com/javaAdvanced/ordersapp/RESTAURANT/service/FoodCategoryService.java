package com.javaAdvanced.ordersapp.RESTAURANT.service;

import com.javaAdvanced.ordersapp.EXCEPTIONS.FoodCategoryAlreadyExists;
import com.javaAdvanced.ordersapp.EXCEPTIONS.FoodCategoryNotFoundException;
import com.javaAdvanced.ordersapp.RESTAURANT.dao.FoodCategoryRepository;
import com.javaAdvanced.ordersapp.RESTAURANT.model.FoodCategoryEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.model.RestaurantEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FoodCategoryService {

    private FoodCategoryRepository foodCategoryRepository;

    @Autowired
    public FoodCategoryService(FoodCategoryRepository foodCategoryRepository){
        this.foodCategoryRepository = foodCategoryRepository;
    }


    public FoodCategoryEntity createFoodCategory(FoodCategoryEntity foodCategoryEntity, long restaurantId)  {
        if(foodCategoryRepository.findByNameAndRestaurantId(restaurantId,foodCategoryEntity.getName().toLowerCase()).isPresent()){
            throw new FoodCategoryAlreadyExists("This food category already exists!");
        }
        foodCategoryEntity.setName(foodCategoryEntity.getName().toLowerCase());
        return foodCategoryRepository.save(foodCategoryEntity);
    }

    public List<FoodCategoryEntity> getAllFoodCategories(long restaurantId){
        return foodCategoryRepository.findFoodCategoriesByRestaurantId(restaurantId);

    }
     public FoodCategoryEntity getFoodCategoryById(long foodCategoryId){
        if(!foodCategoryRepository.findById(foodCategoryId).isPresent()) {
            throw new FoodCategoryNotFoundException("Food category with id " + foodCategoryId + " not found");
        }
        return foodCategoryRepository.findById(foodCategoryId).get();
     }

     public void updateFoodCategory(long foodCategoryId, FoodCategoryEntity foodCategoryEntity){
        FoodCategoryEntity foodCategoryEntity1 = getFoodCategoryById(foodCategoryId);
        foodCategoryRepository.update(foodCategoryId,foodCategoryEntity.getName().toLowerCase());
     }

    public void deleteFoodCategory(long restaurantId, long foodCategoryId){
        FoodCategoryEntity foodCategoryEntity1 = getFoodCategoryById(foodCategoryId);
        foodCategoryRepository.deleteByFoodCategoryIdAndRestaurantId(restaurantId, foodCategoryId);
    }

}
