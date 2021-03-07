package com.javaAdvanced.ordersapp.USER.service;

import com.javaAdvanced.ordersapp.USER.model.UserDTO;
import com.javaAdvanced.ordersapp.USER.model.UserEntity;
import com.javaAdvanced.ordersapp.USER.dao.UserRepository;
import com.javaAdvanced.ordersapp.EXCEPTIONS.InvalidPasswordException;
import com.javaAdvanced.ordersapp.EXCEPTIONS.UsedEmailException;
import com.javaAdvanced.ordersapp.EXCEPTIONS.UserNotFoundException;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String generateCommonLangPassword() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
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

    public UserEntity getUserByEmail(String email) {
        if(!userRepository.findByEmail(email).isPresent()){
            throw new UserNotFoundException("User with email " + email + " not found!");
        }
        return userRepository.findByEmail(email).get();
    }


    public  boolean isEmailValid(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private boolean isPasswordSecure(String password) {
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
        if(!isEmailValid(user.getEmail())){
            throw new UsedEmailException("This email is not valid!");
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
        userRepository.update(id,userUpdated.getEmail(),
                              this.passwordEncoder.encode(userUpdated.getPassword()),
                              userUpdated.getRole().ordinal());
    }

    public void updatePassword(UserEntity userEntity, String newPassword) {
        if(!isPasswordSecure(newPassword)){
            throw new InvalidPasswordException("The password must contain letters and digits and must be at least " +
                    "8chars long!");
        }

        userRepository.update(userEntity.getId(),userEntity.getEmail(),
                            this.passwordEncoder.encode(newPassword),
                            userEntity.getRole().ordinal());
    }

    public void deleteUser(long id) {
        UserEntity user = getUserById(id);
        userRepository.deleteById(id);
    }
}

