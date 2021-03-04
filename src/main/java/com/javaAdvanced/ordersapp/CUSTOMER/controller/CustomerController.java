package com.javaAdvanced.ordersapp.CUSTOMER.controller;

import com.javaAdvanced.ordersapp.CUSTOMER.model.CustomerDTO;
import com.javaAdvanced.ordersapp.CUSTOMER.model.CustomerEntity;
import com.javaAdvanced.ordersapp.CUSTOMER.service.CustomerService;
import com.javaAdvanced.ordersapp.EMAIL.EmailService;
import com.javaAdvanced.ordersapp.USER.model.UserDTO;
import com.javaAdvanced.ordersapp.USER.dao.Role;;
import com.javaAdvanced.ordersapp.USER.model.UserEntity;
import com.javaAdvanced.ordersapp.USER.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    private UserService userService;
    private CustomerService customerService;
    private EmailService emailService;

    @Autowired
    public CustomerController(@Lazy UserService userService,
                              @Lazy CustomerService customerService,
                              @Lazy EmailService emailService) {
        this.userService     = userService;
        this.customerService = customerService;
        this.emailService    = emailService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerEntity>> getAllCustomers(){
        return new ResponseEntity<List<CustomerEntity>>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping ("{id}")
    public ResponseEntity <CustomerEntity> getCustomerById(@PathVariable int id)  {
        return new ResponseEntity<CustomerEntity>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CustomerDTO customerDTO)  {
        UserDTO user = new UserDTO(customerDTO.getEmail(), customerDTO.getPassword(),Role.CUSTOMER);
        UserEntity userEntity = userService.createUser(user);
        customerService.createCustomer(customerDTO, userEntity);
        emailService.send(customerDTO.getEmail(),
                       "Dear" + customerDTO.getName()+" welcome to our application!");
        return new ResponseEntity<>("Customer created! ", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable int id,
                                                 @RequestBody CustomerDTO customer)  {
        UserDTO user = new UserDTO(customer.getEmail(), customer.getPassword(),Role.CUSTOMER);
        userService.updateUser(customerService.getCustomerById(id).getUserEntity().getId(),user);
        customerService.updateCustomer(id,customer);
        return new ResponseEntity<String>("Customer updated! ", HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteRCustomer(@PathVariable int id)  {
        userService.deleteUser(customerService.getCustomerById(id).getUserEntity().getId());
        return new ResponseEntity<>("Customer deleted! ", HttpStatus.OK);
    }
}

