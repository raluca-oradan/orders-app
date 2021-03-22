package com.javaAdvanced.ordersapp.FOOD_ITEM.service;

import com.javaAdvanced.ordersapp.EXCEPTIONS.FoodCategoryNotFoundException;
import com.javaAdvanced.ordersapp.EXCEPTIONS.FoodItemAlreadyExists;
import com.javaAdvanced.ordersapp.EXCEPTIONS.FoodItemNotFoundException;
import com.javaAdvanced.ordersapp.FOOD_ITEM.dao.FoodItemRepository;
import com.javaAdvanced.ordersapp.FOOD_ITEM.model.FoodItemEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.model.FoodCategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodItemService {
    private FoodItemRepository foodItemRepository;

    @Autowired
    public FoodItemService(FoodItemRepository foodItemRepostory){
        this.foodItemRepository = foodItemRepostory;
    }

    public FoodItemEntity createFoodItem(long foodCategoryId,FoodItemEntity foodItemEntity){
        if(foodItemRepository.findFoodItemByFoodCategoryIdAndFoodItemName(foodCategoryId,
                                                            foodItemEntity.getName().toLowerCase()).isPresent()){
            throw  new FoodItemAlreadyExists("This food item already exists!");
        }
        return  foodItemRepository.save(foodItemEntity);
    }

    public FoodItemEntity getFoodItemById(long foodItemId){
        if(!foodItemRepository.findById(foodItemId).isPresent()) {
            throw new FoodItemNotFoundException("Food category with id " + foodItemId + " not found");
        }
        return foodItemRepository.findById(foodItemId).get();
    }

    public List<FoodItemEntity> getAllFoodItems(long foodCategoryId){
        return foodItemRepository.findFoodItemsByFoodCategoryId(foodCategoryId);
    }

    public void updateFoodItem(long foodItemId, FoodItemEntity foodItemEntity){
        FoodItemEntity foodItemEntity1 = getFoodItemById(foodItemId);
        foodItemRepository.update(foodItemId,foodItemEntity.getName().toLowerCase(), foodItemEntity.getDescription(),
                                    foodItemEntity.getPrice(),foodItemEntity.getWeight());
    }

    public void deleteFoodItem(long foodCategoryId, long foodItemId){
        FoodItemEntity foodItemEntity1 = getFoodItemById(foodItemId);
        foodItemRepository.deleteByFoodCategoryIdAndFoodItemId(foodCategoryId, foodItemId);
    }
}
