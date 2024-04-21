package com.commic.v1.api;

import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.services.attendance.IAttendanceServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {
    @Autowired
    IAttendanceServices attendanceServices;

    @PostMapping
    public APIResponse attendance(@RequestParam("userId") Integer userId) {
        APIResponse apiResponse = new APIResponse();
        if (userId == null) {
            apiResponse.setCode(ErrorCode.PARAMETER_MISSING.getCode());
            apiResponse.setMessage(ErrorCode.PARAMETER_MISSING.getMessage());
            return apiResponse;
        }

        boolean isSuccess = attendanceServices.createAttendance(userId);
        if (isSuccess) {
            apiResponse.setCode(ErrorCode.CREATE_SUCCESS.getCode());
            apiResponse.setMessage(ErrorCode.CREATE_SUCCESS.getMessage());
            return apiResponse;
        } else {
            apiResponse.setCode(ErrorCode.CREATE_FAILED.getCode());
            apiResponse.setMessage(ErrorCode.CREATE_FAILED.getMessage());
            return apiResponse;
        }
    }
}
