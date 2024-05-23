package com.commic.v1.dto.responses;

import lombok.Data;

import java.sql.Date;

@Data
public class AttendanceResponse {
    private Double point;
    private Date date;
    private Integer dateAttendanceContinuous;
    private Double totalPoint;
}
