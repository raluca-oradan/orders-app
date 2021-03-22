package com.javaAdvanced.ordersapp.ORDER.service;

import com.javaAdvanced.ordersapp.CUSTOMER.model.CustomerEntity;
import com.javaAdvanced.ordersapp.CUSTOMER.service.CustomerService;
import com.javaAdvanced.ordersapp.EXCEPTIONS.CustomerNotFoundException;
import com.javaAdvanced.ordersapp.EXCEPTIONS.ForbiddenAccesException;
import com.javaAdvanced.ordersapp.EXCEPTIONS.OrderNotFoundException;
import com.javaAdvanced.ordersapp.FOOD_ITEM.model.FoodItemEntity;
import com.javaAdvanced.ordersapp.ORDER.dao.OrderRepository;
import com.javaAdvanced.ordersapp.ORDER.model.OrderDTO;
import com.javaAdvanced.ordersapp.ORDER.model.OrderEntity;
import com.javaAdvanced.ordersapp.RESTAURANT.model.RestaurantEntity;
import com.javaAdvanced.ordersapp.SECURITY.jwt.JWTprovider;
import com.javaAdvanced.ordersapp.USER.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private CustomerService customerService;
    private JWTprovider jwtProvider;


    @Autowired
    public OrderService(OrderRepository orderRepository,
                        @Lazy CustomerService customerService,
                        @Lazy JWTprovider jwtProvider){
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.jwtProvider     = jwtProvider;
    }

    public OrderEntity createOrder(OrderDTO orderDTO,
                                   CustomerEntity customerEntity,
                                   RestaurantEntity restaurantEntity){
        if(customerEntity == null){
            throw new CustomerNotFoundException("Customer not found!");
        }
        OrderEntity orderEntity = new OrderEntity();
        //TODO check the order number for this restaurant and update it
        orderEntity.setOrderNo(1);
        orderEntity.setOrderDescription(orderDTO.getOrderDescription());
        orderEntity.setInfo(orderDTO.getInfo());
        orderEntity.setRestaurantEntity(restaurantEntity);
        orderEntity.setCustomerEntity(customerEntity);
        return this.orderRepository.save(orderEntity);
    }

    public OrderEntity getOrderById(long orderId){
        if(!orderRepository.findById(orderId).isPresent()) {
            throw new OrderNotFoundException("Order with id " + orderId + " not found");
        }
        return orderRepository.findById(orderId).get();
    }


    public void checkCustomerEmail(long orderId, String jwt) {
        CustomerEntity customerEntity = getOrderById(orderId).getCustomerEntity();
        if(customerEntity == null){
            throw new CustomerNotFoundException("Customer not found!");
        }
        UserEntity userEntity = customerService.getCustomerById(customerEntity.getId()).getUserEntity();
        String email = jwtProvider.getSubjectFromJWT(jwt);
        if (!userEntity.getEmail().equals(email)) {
            throw new ForbiddenAccesException("You are not allowed to do this action!");
        }
    }

    public List<OrderEntity> getAllOrdersByRestaurantId(CustomerEntity customerEntity, RestaurantEntity restaurantEntity){
        return orderRepository.findOrdersByCustomerIdAndRestaurantId(customerEntity.getId(),restaurantEntity.getId());
    }

    public List<OrderEntity> getAllOrders(CustomerEntity customerEntity){
        return orderRepository.findOrdersByCustomerId(customerEntity.getId());
    }

    public void updateOrder(long id, OrderDTO orderDto) {
        OrderEntity orderEntity = getOrderById(id);
        orderRepository.update(id, orderDto.getOrderNo(), orderDto.getOrderDescription(),
                orderDto.getInfo());
    }

    public void deleteOrder(long restaurantId, long orderId){
        OrderEntity orderEntity = getOrderById(orderId);
        orderRepository.deleteByRestaurantIdAndOrderId(restaurantId, orderId);
    }

}
