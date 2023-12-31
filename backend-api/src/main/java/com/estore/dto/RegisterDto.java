package com.estore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterDto {
    @NotEmpty(message = "Email address is required")
    private String email;

    @NotEmpty(message = "Phone number is required!")
    private String phone;

    @NotEmpty
    private String name;

    @NotEmpty
//    @Min(value = 8, message = "Password must have at least 8 character!")
    private String password;

    @NotEmpty
    private String confirmPassword;

}
