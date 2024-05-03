package com.commic.v1.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    private String email;
    private String otp;
    private String password;
    @NotBlank(message = "PARAMETER_MISSING")
    private String confirmPassword;
    @NotBlank(message = "PARAMETER_MISSING")
    private String newPassword;
}
