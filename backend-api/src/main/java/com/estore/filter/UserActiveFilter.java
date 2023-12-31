package com.estore.filter;

import com.estore.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.NotActiveException;

public class UserActiveFilter extends OncePerRequestFilter {
    @Autowired
    UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, NotActiveException {
//        System.out.println(userRepository.findByEmail("client1@esi.com"));
//        try{
//            List< User> users = userRepository.findByEmail("client2@esi.com");
//            System.out.println(users);
//
//        }catch (Exception e){
//
//        }
            filterChain.doFilter(request, response);
    }
}
