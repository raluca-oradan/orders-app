package com.javaAdvanced.ordersapp.USER.model;
import com.javaAdvanced.ordersapp.USER.dao.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class CurrentUser implements UserDetails {

    private String email;
    private String password;
    private Set<GrantedAuthority> roles;


    public CurrentUser(String email, String password, Role role) {
        this.email    = email;
        this.password = password;
        this.roles    = new HashSet<>();
        this.roles.add(new SimpleGrantedAuthority("ROLE_"+ role.name()));
    }

    public static CurrentUser create(UserEntity userEntity){
        return new CurrentUser(
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRole());
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

