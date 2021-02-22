package com.javaAdvanced.ordersapp.RESTAURANT.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    @Query(value = "SELECT * FROM cars c " +
            "WHERE c.user_id = :user_entity_id", nativeQuery = true)
    public RestaurantEntity getRestaurantByUserEntityId(long user_entity_id);

    @Modifying //e pentru ca modificam starea bazei de date
    @Transactional //e pentru ca daca cumva update-ul failuie intr-un mod sa stie sa revina la starea de la care s-a
    // plecat. Poate tu dai acolo la update o parola mai lunga decat e limita pusa pe baza de date si
    // da eroare, nah, anotarea asta face ca parola sa ramana la fel in caz de erori
    @Query(value = " UPDATE restaurants " +
            " SET name = :restaurantName, location = :restaurantLocation, description = :restaurantDescription" +
            " WHERE id = :id ", nativeQuery = true)
    public void update(long id, String restaurantName, String restaurantLocation, String restaurantDescription);
}
