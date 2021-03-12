package com.javaAdvanced.ordersapp.CUSTOMER.service;

import com.javaAdvanced.ordersapp.CUSTOMER.model.CustomerDTO;
import com.javaAdvanced.ordersapp.CUSTOMER.model.CustomerEntity;
import com.javaAdvanced.ordersapp.CUSTOMER.dao.CustomerRepository;
import com.javaAdvanced.ordersapp.EXCEPTIONS.UserNotFoundException;
import com.javaAdvanced.ordersapp.USER.model.UserEntity;
import com.javaAdvanced.ordersapp.USER.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerService {

        private CustomerRepository customerRepository;


        @Autowired
        public CustomerService(CustomerRepository customerRepository){
            this.customerRepository = customerRepository;
        }

        public List<CustomerEntity> getAllCustomers(){
            return customerRepository.findAll();
        }

        public CustomerEntity getCustomerById(long id) {
            if((!customerRepository.findById(id).isPresent())){
                throw new UserNotFoundException("Customer with id " + id + " not found!");
            }
            return customerRepository.findById(id).get();
        }

        public CustomerEntity createCustomer(CustomerDTO customer, UserEntity userEntity)  {
            CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setName(customer.getName());
            customerEntity.setPhone_number(customer.getPhone_number());
            customerEntity.setAddress(customer.getAddress());
            customerEntity.setUserEntity(userEntity);
            return customerRepository.save(customerEntity);
        }

        public void updateCustomer(long id, CustomerDTO customerUpdated) {
            CustomerEntity customer = getCustomerById(id);
            customerRepository.update(id,customerUpdated.getName(),customerUpdated.getPhone_number(),
                                        customerUpdated.getAddress());
        }

        public void deleteCustomer(long id) {
            CustomerEntity customer = getCustomerById(id);
            customerRepository.deleteById(id);
        }
    }
