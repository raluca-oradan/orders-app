package com.javaAdvanced.ordersapp.USER.api;

import com.javaAdvanced.ordersapp.USER.dao.UserEntity;
import com.javaAdvanced.ordersapp.USER.model.JWTmodel;
import com.javaAdvanced.ordersapp.USER.model.LoginRequest;
import com.javaAdvanced.ordersapp.USER.security.JWTprovider;
import com.javaAdvanced.ordersapp.USER.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private JWTprovider tokenProvider;
    private UserService userService;
    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JWTprovider tokenProvider,
                                    UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider         = tokenProvider;
        this.userService           = userService;
    }
    @PostMapping("/login")
    public ResponseEntity<JWTmodel> login(@RequestBody LoginRequest loginRequest) {
        System.out.println(loginRequest);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateJWToken(authentication);
        return ResponseEntity.ok(new JWTmodel(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO user) {
        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

