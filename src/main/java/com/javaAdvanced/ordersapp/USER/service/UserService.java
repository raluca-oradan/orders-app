package com.javaAdvanced.ordersapp.USER.service;

import com.javaAdvanced.ordersapp.USER.api.UserDTO;
import com.javaAdvanced.ordersapp.USER.dao.UserEntity;
import com.javaAdvanced.ordersapp.USER.dao.UserRepository;
import com.javaAdvanced.ordersapp.USER.exceptions.InvalidPasswordException;
import com.javaAdvanced.ordersapp.USER.exceptions.UsedEmailException;
import com.javaAdvanced.ordersapp.USER.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }

    public UserEntity getUserById(long id) {
        if((!userRepository.findById(id).isPresent())){
            throw new UserNotFoundException("User with id " + id + " not found!");
        }
        return userRepository.findById(id).get();
    }


    private Boolean isPasswordSecure(String password) {
        if (password.length() < 8) {
            return false;
        }
        int countD = 0;
        int countL = 0;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                countD++;
            } else if (Character.isLetter(password.charAt(i))) {
                countL++;
            }
        }
        if (countL == password.length() || countD == password.length()) {
            return false;
        }
        return true;
    }

    public UserEntity createUser(UserDTO user)  {
        if(!isPasswordSecure(user.getPassword())){
            throw new InvalidPasswordException("The password must contain letters and digits and must be at least " +
                    "8chars long!");
        }
        if(userRepository.isEmailAlreadyUsed(user.getEmail())){
            throw new UsedEmailException("This email is already used!");
        }
        UserEntity u = new UserEntity();
        u.setEmail(user.getEmail());
        u.setPassword(this.passwordEncoder.encode(user.getPassword()));
        u.setRole(user.getRole());
        return userRepository.save(u);
    }

    public void updateUser(long id, UserDTO userUpdated) {
        UserEntity user = getUserById(id);
        if(userRepository.isEmailAlreadyUsed(userUpdated.getEmail())){
            throw new UsedEmailException("This email is already used!");
        }
        if(!isPasswordSecure(userUpdated.getPassword())){
            throw new InvalidPasswordException("The password must contain letters and digits and must be at least " +
                    "8chars long!");
        }
        userRepository.update(id,userUpdated.getEmail(),userUpdated.getPassword(), userUpdated.getRole().toString());
    }

    public void deleteUser(long id) {
        UserEntity user = getUserById(id);
        userRepository.deleteById(id);
    }
}

