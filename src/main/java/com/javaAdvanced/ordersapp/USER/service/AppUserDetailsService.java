package com.javaAdvanced.ordersapp.USER.service;
import com.javaAdvanced.ordersapp.USER.dao.UserEntity;
import com.javaAdvanced.ordersapp.USER.dao.UserRepository;
import com.javaAdvanced.ordersapp.USER.exceptions.UserNotFoundException;
import com.javaAdvanced.ordersapp.USER.model.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User with email " + email + " does not exists");
        }
        return CurrentUser.create(user.get());
    }
}
