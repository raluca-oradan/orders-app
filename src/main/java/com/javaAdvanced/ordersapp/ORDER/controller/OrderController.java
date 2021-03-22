package com.javaAdvanced.ordersapp.ORDER.controller;


import com.javaAdvanced.ordersapp.CUSTOMER.model.CustomerEntity;
import com.javaAdvanced.ordersapp.CUSTOMER.service.CustomerService;
import com.javaAdvanced.ordersapp.EXCEPTIONS.ForbiddenAccesException;
import com.javaAdvanced.ordersapp.FOOD_ITEM.model.FoodItemEntity;
import com.javaAdvanced.ordersapp.ORDER.model.OrderDTO;
import com.javaAdvanced.ordersapp.ORDER.model.OrderEntity;
import com.javaAdvanced.ordersapp.ORDER.service.OrderService;
import com.javaAdvanced.ordersapp.RESTAURANT.model.RestaurantEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.service.RestaurantService;
import com.javaAdvanced.ordersapp.SECURITY.jwt.JWTprovider;
import com.javaAdvanced.ordersapp.USER.model.UserEntity;
import com.javaAdvanced.ordersapp.USER.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private OrderService orderService;
    private JWTprovider jwtProvider;
    private UserService userService;
    private RestaurantService restaurantService;
    private CustomerService customerService;

    @Autowired
    public OrderController(@Lazy OrderService orderService,
                           @Lazy JWTprovider jwtProvider,
                           @Lazy UserService userService,
                           @Lazy RestaurantService restaurantService,
                           @Lazy CustomerService customerService){
        this.orderService      = orderService;
        this.jwtProvider       = jwtProvider;
        this.userService       = userService;
        this.restaurantService = restaurantService;
        this.customerService   = customerService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/{restaurantId}")
    public ResponseEntity<OrderEntity> createOrder(@PathVariable int restaurantId,
                                                   @RequestBody OrderDTO orderDTO,
                                                   @RequestHeader("Authorization") String jwt){
        RestaurantEntity restaurantEntity = restaurantService.getRestaurantById(restaurantId);
        String email = jwtProvider.getSubjectFromJWT(jwt);
        CustomerEntity customerEntity = userService.
               getUserByEmail(email).
               getCustomerEntity();
        OrderEntity orderEntity = orderService.createOrder(orderDTO,customerEntity,restaurantEntity);
        return ResponseEntity.ok(orderEntity);
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{restaurantId}/{orderId}")
    public ResponseEntity <OrderEntity> getOrderById(@PathVariable int restaurantId,
                                                     @PathVariable int orderId,
                                                     @RequestHeader("Authorization") String jwt){
        orderService.checkCustomerEmail(orderId,jwt);
        restaurantService.getRestaurantById(restaurantId);
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/{restaurantId}")
    public ResponseEntity <List<OrderEntity>> getAllOrdersByRestaurantId(@PathVariable int restaurantId,
                                                                            @RequestHeader("Authorization") String jwt){
        RestaurantEntity restaurantEntity = restaurantService.getRestaurantById(restaurantId);
        String email = jwtProvider.getSubjectFromJWT(jwt);
        CustomerEntity customerEntity = userService.
                getUserByEmail(email).
                getCustomerEntity();
        return ResponseEntity.ok(orderService.getAllOrdersByRestaurantId(customerEntity,restaurantEntity));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping()
    public ResponseEntity <List<OrderEntity>> getAllOrders(@RequestHeader("Authorization") String jwt){
        String email = jwtProvider.getSubjectFromJWT(jwt);
        CustomerEntity customerEntity = userService.
                getUserByEmail(email).
                getCustomerEntity();
        return ResponseEntity.ok(orderService.getAllOrders(customerEntity));
    }

    @PutMapping("/{restaurantId}/{orderId}")
    public ResponseEntity <String> updateOrder(@PathVariable int restaurantId,
                                               @RequestBody OrderDTO orderDTO,
                                               @PathVariable int orderId,
                                               @RequestHeader("Authorization") String jwt){
        orderService.checkCustomerEmail(orderId,jwt);
        restaurantService.getRestaurantById(restaurantId);
        orderService.updateOrder(orderId,orderDTO);
        return ResponseEntity.ok("Order updated!");
    }

    @DeleteMapping("/{restaurantId}/{orderId}")
    public ResponseEntity <String> deleteOrder(@PathVariable int restaurantId,
                                               @PathVariable int orderId,
                                               @RequestHeader("Authorization") String jwt){
        orderService.checkCustomerEmail(orderId,jwt);
        restaurantService.getRestaurantById(restaurantId);
        orderService.deleteOrder(restaurantId,orderId);
        return ResponseEntity.ok("Order deleted!");
    }

}
