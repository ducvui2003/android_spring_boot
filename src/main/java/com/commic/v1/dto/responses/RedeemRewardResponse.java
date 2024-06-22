package com.commic.v1.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedeemRewardResponse {
    private Integer id;
    private String name;
    private Integer point;
    private String image;
    private Date date;
    private Boolean isExchange;
}
