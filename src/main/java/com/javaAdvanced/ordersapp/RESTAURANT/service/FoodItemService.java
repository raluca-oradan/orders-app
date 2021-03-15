package com.javaAdvanced.ordersapp.RESTAURANT.service;

import com.javaAdvanced.ordersapp.EXCEPTIONS.FoodCategoryAlreadyExists;
import com.javaAdvanced.ordersapp.EXCEPTIONS.FoodCategoryNotFoundException;
import com.javaAdvanced.ordersapp.RESTAURANT.dao.FoodCategoryRepository;
import com.javaAdvanced.ordersapp.RESTAURANT.dao.FoodItemRepository;
import com.javaAdvanced.ordersapp.RESTAURANT.model.FoodItemEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemService {

    private FoodItemRepository foodItemRepository;
    private FoodCategoryRepository foodCategoryRepository;

     @Autowired
    public FoodItemService(FoodItemRepository foodItemRepository,
                           FoodCategoryRepository foodCategoryRepository){
         this.foodItemRepository     = foodItemRepository;
         this.foodCategoryRepository = foodCategoryRepository;
    }

    public FoodItemEntity createFoodItem(FoodItemEntity foodItemEntity, long foodCategoryId)  {
        if(foodItemRepository.findByNameAndFoodCategoryId(foodCategoryId,
                                                         foodItemEntity.getName().toLowerCase()).isPresent()){
            throw  new FoodCategoryAlreadyExists("This food item already exists!");
        }
        foodItemEntity.setName(foodItemEntity.getName().toLowerCase());
        return foodItemRepository.save(foodItemEntity);
    }

    public FoodItemEntity getFoodItemById(long foodItemId){
         if(!foodItemRepository.findById(foodItemId).isPresent()){
             throw  new FoodCategoryNotFoundException("Food item with id " + foodItemId + " does not exists!");
         }
         return foodItemRepository.findById(foodItemId).get();
    }

    public List<FoodItemEntity> getAllFoodItems(long foodCategoryId){
         return foodItemRepository.findFoodItemsByFoodCategoryId(foodCategoryId);
    }

    public void updateFoodItem(long foodItemId, FoodItemEntity foodItemEntity){
         FoodItemEntity foodItemEntity1 = getFoodItemById(foodItemId);
         foodItemRepository.update(foodItemId,foodItemEntity.getName().toLowerCase(),
                                    foodItemEntity.getDescription(),
                                    foodItemEntity.getPrice(),
                                    foodItemEntity.getWeight());
    }

    public void deleteFoodItem(long foodCategoryId, long foodItemId){
        FoodItemEntity foodItemEntity1 = getFoodItemById(foodItemId);
        foodItemRepository.deleteByFoodCategoryIdAndFoodItemId(foodCategoryId,foodItemId);
    }
}
