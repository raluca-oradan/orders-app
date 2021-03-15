package com.javaAdvanced.ordersapp.RESTAURANT.dao;

import com.javaAdvanced.ordersapp.RESTAURANT.model.FoodCategoryEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.model.FoodItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItemEntity,Long> {

    @Query(value = " SELECT * FROM food_items " +
            " WHERE food_category_id = :foodCategoryId and name = :foodItemName", nativeQuery = true)
    public Optional<FoodItemEntity> findByNameAndFoodCategoryId(long foodCategoryId, String foodItemName);

    @Modifying
    @Transactional
    @Query(value = " SELECT * FROM food_items " +
          " WHERE food_category_id = :foodCategoryId", nativeQuery = true)
    public List<FoodItemEntity> findFoodItemsByFoodCategoryId(long foodCategoryId);

    @Modifying
    @Transactional
    @Query(value = " UPDATE food_items " +
            " SET name = :foodItemName, " +
            "description = :foodItemDescription, " +
            "price = :foodItemPrice, " +
            "weight = :foodItemWeight" +
            " WHERE id = :id ", nativeQuery = true)
    public void update(long id, String foodItemName,
                       String foodItemDescription,
                       java.math.BigDecimal foodItemPrice,
                       java.math.BigDecimal foodItemWeight);

    @Modifying
    @Transactional
    @Query(value = " DELETE FROM food_items " +
            " WHERE food_category_id = :foodCategoryId and id = :foodItemId", nativeQuery = true)
    public void deleteByFoodCategoryIdAndFoodItemId(long foodCategoryId, long foodItemId);
}
