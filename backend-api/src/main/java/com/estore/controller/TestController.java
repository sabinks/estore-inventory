package com.estore.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class    TestController {
    @PreAuthorize("hasAnyRole('SUPERADMIN', 'ADMIN', 'CLIENT')")
    @GetMapping("/test")
    public String test(){
        return "I am test!";
    }
}
