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
    @NotBlank(message = "PARAMETER_MISSING")
    private String email;
    @NotBlank(message = "PARAMETER_MISSING")
    private String otp;
    @NotBlank(message = "PARAMETER_MISSING")
    private String password;
    @NotBlank(message = "PARAMETER_MISSING")
    private String confirmPassword;
}
