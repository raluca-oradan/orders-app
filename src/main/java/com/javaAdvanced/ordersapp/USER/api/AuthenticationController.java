package com.javaAdvanced.ordersapp.USER.api;

import com.javaAdvanced.ordersapp.EMAIL.EmailService;
import com.javaAdvanced.ordersapp.SECURITY.jwt.JWTRedisService;
import com.javaAdvanced.ordersapp.USER.model.ForgotPassword;
import com.javaAdvanced.ordersapp.USER.model.JWTmodel;
import com.javaAdvanced.ordersapp.USER.model.LoginRequest;
import com.javaAdvanced.ordersapp.SECURITY.jwt.JWTprovider;
import com.javaAdvanced.ordersapp.USER.model.ResetPasswordDTO;
import com.javaAdvanced.ordersapp.USER.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private JWTprovider           tokenProvider;
    private UserService           userService;
    private JWTRedisService       jwtRedisService;
    private EmailService          emailService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JWTprovider tokenProvider,
                                    UserService userService,
                                    JWTRedisService jwtRedisService,
                                    EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider         = tokenProvider;
        this.userService           = userService;
        this.jwtRedisService       = jwtRedisService;
        this.emailService          = emailService;

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


    @PostMapping("/resetPassword")
    public ResponseEntity resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO,
                                        @RequestHeader ("Authorization") String jwt ){
        String userEmail = tokenProvider.getSubjectFromJWT(jwt);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userEmail,
                        resetPasswordDTO.getCurrentPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        userService.resetPassword(userEmail,resetPasswordDTO.getNewPassword(), resetPasswordDTO.getConfirmedNewPassword());
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/forgotPassword")
        public ResponseEntity forgotPassword(@RequestBody ForgotPassword email){
        String newPassword = userService.generateCommonLangPassword();
        userService.getUserByEmail(email.getEmail());
        userService.resetPassword(email.getEmail(),newPassword, newPassword);
        emailService.send(email.getEmail(), "Your new password is: " + newPassword + "\n " +
                          "Please reset it after first login");
        return  ResponseEntity.ok().build();
        }

    @PostMapping("/logout")
        public ResponseEntity<String> logout(@RequestHeader ("Authorization") String jwt) {
        String userEmail = tokenProvider.getSubjectFromJWT(jwt);
        jwtRedisService.invalidateJWT(jwt,userEmail);
        return ResponseEntity.ok().build();
    }
}

