package com.javaAdvanced.ordersapp.USER.api;

import com.javaAdvanced.ordersapp.SECURITY.jwt.JWTRedisService;
import com.javaAdvanced.ordersapp.USER.model.JWTmodel;
import com.javaAdvanced.ordersapp.USER.model.LoginRequest;
import com.javaAdvanced.ordersapp.SECURITY.jwt.JWTprovider;
import com.javaAdvanced.ordersapp.USER.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private JWTprovider           tokenProvider;
    private UserService           userService;
    private JWTRedisService       jwtRedisService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JWTprovider tokenProvider,
                                    UserService userService,
                                    JWTRedisService jwtRedisService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider         = tokenProvider;
        this.userService           = userService;
        this.jwtRedisService       = jwtRedisService;
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

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader ("Authorization") String jwt) {
        String userEmail = tokenProvider.getSubjectFromJWT(jwt);
        jwtRedisService.invalidateJWT(jwt,userEmail);
        return ResponseEntity.ok().build();
    }
}

