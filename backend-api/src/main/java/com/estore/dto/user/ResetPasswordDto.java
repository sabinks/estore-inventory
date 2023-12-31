package com.estore.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Data
@Getter
@Setter
@Service
public class ResetPasswordDto {
    @NotNull
    private Long id;

    @NotEmpty
    private String resetToken;

    @NotEmpty
    private String password;
}
