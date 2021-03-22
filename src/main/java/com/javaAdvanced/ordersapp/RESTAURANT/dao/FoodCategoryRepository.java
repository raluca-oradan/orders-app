package com.javaAdvanced.ordersapp.RESTAURANT.dao;

import com.javaAdvanced.ordersapp.RESTAURANT.model.FoodCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategoryEntity,Long> {


    @Query(value = " SELECT * FROM food_categories " +
            " WHERE restaurant_id = :restaurantId and name = :foodCategoryName", nativeQuery = true)
    public Optional<FoodCategoryEntity> findByNameAndRestaurantId(long restaurantId, String foodCategoryName);

    @Modifying //e pentru ca modificam starea bazei de date
    @Transactional //e pentru ca daca cumva update-ul failuie intr-un mod sa stie sa revina la starea de la care s-a
    // plecat. Poate tu dai acolo la update o parola mai lunga decat e limita pusa pe baza de date si
    // da eroare, nah, anotarea asta face ca parola sa ramana la fel in caz de erori
    @Query(value = " UPDATE food_categories " +
            " SET name = :foodCategoryName" +
            " WHERE id = :id ", nativeQuery = true)
    public void update(long id, String foodCategoryName);

    @Modifying
    @Transactional
    @Query(value = " SELECT * FROM food_categories " +
            " WHERE restaurant_id = :id ", nativeQuery = true)
    public List<FoodCategoryEntity> findFoodCategoriesByRestaurantId(long id);


    @Modifying
    @Transactional
    @Query(value = " DELETE FROM food_categories " +
            " WHERE restaurant_id = :restaurantId and id = :foodCategoryId", nativeQuery = true)
    public void deleteByFoodCategoryIdAndRestaurantId(long restaurantId, long foodCategoryId);
}
