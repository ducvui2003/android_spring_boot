package com.commic.v1.dto.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountVerifyRequest {
    private String email;
    private String otp;

}
