package com.commic.v1.api.user;

import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.AttendanceResponse;
import com.commic.v1.dto.responses.UserResponse;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.jwt.JwtTokenUtil;
import com.commic.v1.services.attendance.IAttendanceServices;
import com.commic.v1.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {
    @Autowired
    IAttendanceServices attendanceServices;
    @Autowired
    private IUserService userService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public APIResponse<AttendanceResponse> attendance() {
        APIResponse<AttendanceResponse> apiResponse = new APIResponse();
        AttendanceResponse data = attendanceServices.attendance();
        if (data != null) {
            apiResponse.setCode(ErrorCode.CREATE_SUCCESS.getCode());
            apiResponse.setMessage(ErrorCode.CREATE_SUCCESS.getMessage());
            apiResponse.setResult(data);
        } else {
            apiResponse.setCode(ErrorCode.CREATE_FAILED.getCode());
            apiResponse.setMessage(ErrorCode.CREATE_FAILED.getMessage());
        }
        return apiResponse;
    }
}
