package com.estore.controller;

import com.estore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/email/")
public class EmailVerificationController {

    @Autowired
    UserService userService;

    @GetMapping("/verify/{id}/{verification_token}")
    public ResponseEntity<String> verifyEmail(@PathVariable Long id, @PathVariable String verification_token) throws Exception {
        userService.verifyEmail(id, verification_token);

        return new ResponseEntity<>("Email verification completed", HttpStatus.OK);
    }

}
