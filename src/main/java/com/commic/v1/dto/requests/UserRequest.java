package com.commic.v1.dto.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {
    @NotBlank(message = "PARAMETER_MISSING")
    private String fullName;
    @NotBlank(message = "PARAMETER_MISSING")
    private String phone;
}