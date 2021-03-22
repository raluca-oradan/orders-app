package com.javaAdvanced.ordersapp.SECURITY.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class JWTRedisService {

    private final String JWT = "JWT";// categoria unde vrei sa inserezi elem(redis formeaza categ JWT)
    // insert,get,delete..
    private HashOperations<String, String, String> hashOperations;

    @Autowired
    public JWTRedisService(RedisTemplate<String, String> redisTemplate){
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void invalidateJWT(String jwt, String userEmail){
        this.hashOperations.put(JWT, jwt, userEmail); // jwt - cheia, userEmail - valoarea
    }

    public String getUserEmailFromJWT(String jwt){
        return this.hashOperations.get(JWT, jwt);
    }
}
