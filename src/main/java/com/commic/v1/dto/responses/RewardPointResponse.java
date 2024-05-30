package com.commic.v1.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardPointResponse {
    private Integer dateAttendanceContinuous;
    private Double totalPoint;
}
