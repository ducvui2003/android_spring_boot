package com.commic.v1.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AttendanceResponse {
    private Double point;
    private Date date;
}
