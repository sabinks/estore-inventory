package com.estore.controller;

import com.estore.dto.RegisterDto;
import com.estore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api")
@RestController
public class RegisterController {
    @Autowired
    UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterDto registerDto) throws Exception{
       userService.registerUser(registerDto);

       return new ResponseEntity<String>("User registered!", HttpStatus.CREATED);
    }
}
