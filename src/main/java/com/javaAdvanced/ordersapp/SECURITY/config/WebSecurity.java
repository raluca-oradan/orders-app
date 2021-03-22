package com.javaAdvanced.ordersapp.SECURITY.config;

import com.javaAdvanced.ordersapp.SECURITY.jwt.JWTAuthenticationFilter;
import com.javaAdvanced.ordersapp.SECURITY.jwt.JWTRedisService;
import com.javaAdvanced.ordersapp.SECURITY.jwt.JWTprovider;
import com.javaAdvanced.ordersapp.USER.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final String[]  WHITE_LIST = {
            "/",
            "/api/v1/login",
            "/api/v1/restaurant/register",
            "/api/v1/customer/register",
            "/api/v1/logout",
            "/api/v1/forgotPassword"
    };

    private AppUserDetailsService appUserDetailsService;
    private JWTprovider           jwtProvider;
    private JWTRedisService       jwtRedisService;

    @Autowired
    public WebSecurity(AppUserDetailsService appUserDetailsService,
                       JWTprovider jwtProvider,
                       @Lazy JWTRedisService jwtRedisService){
        this.appUserDetailsService = appUserDetailsService;
        this.jwtProvider           = jwtProvider;
        this.jwtRedisService       = jwtRedisService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, WHITE_LIST)
                .permitAll()
                .anyRequest()
                .authenticated();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{// AuthenticationManagerBuilder e cel care
        auth
                .userDetailsService(appUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception{
        return  super.authenticationManager();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(){
        return new JWTAuthenticationFilter(jwtProvider,
                                           appUserDetailsService,
                                           jwtRedisService); //verifica tokenul la fiecare request
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){ //pentru configurarea redisului
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(){ // clientul
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }
}

