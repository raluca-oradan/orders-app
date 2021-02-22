package com.javaAdvanced.ordersapp.USER.security;

import com.javaAdvanced.ordersapp.USER.service.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private JWTprovider jwtProvider;
    private AppUserDetailsService appUserDetailsService;

    @Autowired
    public JWTAuthenticationFilter(JWTprovider jwtProvider, AppUserDetailsService appUserDetailsService){
        this.jwtProvider           = jwtProvider;
        this.appUserDetailsService = appUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, // pentru fiecare request se aplica acest filtru
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {

            String jwt = this.jwtProvider.getJWTFromRequest(httpServletRequest);
            if(StringUtils.hasText(jwt) && this.jwtProvider.validateToken(jwt)){

                String email = this.jwtProvider.getSubjectFromJWT(jwt);
                UserDetails userDetails = appUserDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e){
            System.out.println("Error in JWTAuthenticationFilter " + e.getMessage());
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
