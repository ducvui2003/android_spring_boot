package com.commic.v1.services.attendance;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.AttendanceResponse;
import com.commic.v1.dto.responses.RewardPointResponse;

public interface IAttendanceServices {
    AttendanceResponse attendance();

    DataListResponse<AttendanceResponse> getHistoryAttendance();

    RewardPointResponse getRedeemReward();
}
