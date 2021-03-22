package com.javaAdvanced.ordersapp.ORDER.dao;

import com.javaAdvanced.ordersapp.FOOD_ITEM.model.FoodItemEntity;
import com.javaAdvanced.ordersapp.ORDER.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    @Query(value = " SELECT * FROM orders " +
            " WHERE customer_id = :customerId and restaurant_id = :restaurantId", nativeQuery = true)
    public List<OrderEntity> findOrdersByCustomerIdAndRestaurantId(long customerId, long restaurantId);

    @Query(value = " SELECT * FROM orders " +
            " WHERE customer_id = :customerId", nativeQuery = true)
    public List<OrderEntity> findOrdersByCustomerId(long customerId);

    @Modifying
    @Transactional
    @Query(value = " UPDATE orders " +
            " SET order_no = :orderNo, order_description = :orderDescription, info = :info" +
            " WHERE id = :id ", nativeQuery = true)
    public void update(long id, int orderNo, String orderDescription, String info);

    @Modifying
    @Transactional
    @Query(value = " DELETE FROM orders " +
            " WHERE restaurant_id = :restaurantId and id = :orderId", nativeQuery = true)
    public void deleteByRestaurantIdAndOrderId(long restaurantId, long orderId);
}
