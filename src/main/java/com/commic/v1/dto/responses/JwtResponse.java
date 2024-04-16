package com.commic.v1.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type;
    private Date expiredTime;

    public JwtResponse(String token, Date expiredTime) {
        this.token = token;
        this.expiredTime = expiredTime;
        this.type = "jwt";
    }
}