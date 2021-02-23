package com.javaAdvanced.ordersapp.SECURITY;

import com.javaAdvanced.ordersapp.USER.model.CurrentUser;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component // creaza acest obiect
public class JWTprovider {

    public final String SECRET_KEY = "99eb89735688ad7a29bb1ff27383bd1005a22a62c97f14357ea4f5f98c1d2c8c01";

    public String generateJWToken(Authentication authentication) { //genereaza tokenul din autentificare

        CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();

        long NOW = new Date().getTime();
        Date expirationDate = new Date(NOW + 86400000); // 86400000 milisecunde = 24h

        return Jwts.builder()
                .setSubject(currentUser.getEmail())//ce sa extraga din token --> emailul
                .setIssuedAt(new Date())//cand s-a creat
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)//ENCODAREA
                .compact();
    }

    public String getJWTFromRequest(HttpServletRequest httpServletRequest) { //extrage tokenul din request
        String jwt = httpServletRequest.getHeader("Authorization");
        if (!StringUtils.hasText(jwt)) {
            return null;
        }
        return jwt;
    }

    public String getSubjectFromJWT(String jwt) {
        Claims claims = Jwts.parser()// interfata face parte din jasonWebToken
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwt)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt);
            return true;
        } catch (SignatureException ex) {
            System.out.println("ERROR" + "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.out.println("ERROR" + "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("ERROR" + "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("ERROR" + "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("ERROR" + "JWT claims string is empty.");
        }
        return false;
    }
}
