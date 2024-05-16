package com.commic.v1.dto.responses;

import lombok.Data;

import java.sql.Date;

@Data
public class AttendanceResponse {
    private Double score;
    private Date date;
}
