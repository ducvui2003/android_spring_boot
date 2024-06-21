package com.commic.v1.dto.responses;

import com.commic.v1.services.attendance.ExchangeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeResponse {
    ExchangeStatus status;
    Double totalScore;
}
