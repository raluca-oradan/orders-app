package com.javaAdvanced.ordersapp.FOOD_ITEM.dao;


import com.javaAdvanced.ordersapp.FOOD_ITEM.model.FoodItemEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.model.FoodCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItemEntity,Long> {

    @Query(value = " SELECT * FROM food_items " +
            "WHERE food_category_id = :foodCategoryId and name = :foodItemName" ,nativeQuery = true)
    public Optional<FoodItemEntity> findFoodItemByFoodCategoryIdAndFoodItemName(Long foodCategoryId, String foodItemName);

    @Modifying
    @Transactional
    @Query(value = " SELECT * FROM food_items " +
            " WHERE food_category_id = :foodCategoryId ", nativeQuery = true)
    public List<FoodItemEntity> findFoodItemsByFoodCategoryId(long foodCategoryId);

    @Modifying
    @Transactional
    @Query(value = " UPDATE food_items " +
            " SET name = :foodItemName, description = :foodItemDescription, " +
                 "price = :foodItemPrice, weight = :foodItemWeight" +
            " WHERE id = :foodItemId ", nativeQuery = true)
    public void update(long foodItemId, String foodItemName, String foodItemDescription,
                       double foodItemPrice, double foodItemWeight);

    @Modifying
    @Transactional
    @Query(value = " DELETE FROM food_items " +
            " WHERE food_category_id = :foodCategoryId and id = :foodItemId", nativeQuery = true)
    public void deleteByFoodCategoryIdAndFoodItemId(long foodCategoryId, long foodItemId);
}
