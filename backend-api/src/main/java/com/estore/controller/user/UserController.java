package com.estore.controller.user;

import com.estore.dto.user.ForgotPasswordDto;
import com.estore.dto.user.ResetPasswordDto;
import com.estore.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordDto forgotPasswordDto)
            throws Exception {
        userService.forgotPassword(forgotPasswordDto);

        return new ResponseEntity<>("Please check your email for password reset link", HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordDto resetPasswordDto)
            throws Exception {
        userService.resetPassword(resetPasswordDto);

        return new ResponseEntity<>("Password reset complete", HttpStatus.OK);
    }
}
