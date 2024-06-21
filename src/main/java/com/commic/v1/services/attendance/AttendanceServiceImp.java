package com.commic.v1.services.attendance;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.AttendanceResponse;
import com.commic.v1.dto.responses.RewardPointResponse;
import com.commic.v1.entities.RewardPoint;
import com.commic.v1.entities.User;
import com.commic.v1.mapper.RewardPointMapper;
import com.commic.v1.repositories.IRedeemRewardRepository;
import com.commic.v1.repositories.IRewardPointRepository;
import com.commic.v1.repositories.IUserRepository;
import com.commic.v1.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

//monday +10d
//friday +15d
@Service
public class AttendanceServiceImp implements IAttendanceServices {
    @Autowired
    IRewardPointRepository rewardPointRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    RewardPointMapper rewardPointMapper;
    @Autowired
    IRedeemRewardRepository redeemRewardRepository;

    @Override
    public AttendanceResponse attendance() {
        AttendanceResponse attendanceResponse = null;
//        Kiểm tra người dùng đã đăng nhập chưa
        User user = SecurityUtils.getUserFromPrincipal(userRepository);
        if (user == null) return attendanceResponse;
        Integer userId = user.getId();
        LocalDate today = LocalDate.now();

//        Kiểm tra xem ngày hôm nay đã điểm danh chưa
        boolean isAttendance = rewardPointRepository.existsByUserIdAndDate(userId, Date.valueOf(today));

//        Nếu đã điểm danh thì trả về false
        if (isAttendance) return attendanceResponse;
        RewardPoint rewardPoint = new RewardPoint();
        rewardPoint.setUser(User.builder().id(userId).build());
        rewardPoint.setDate(Date.valueOf(today));

//        Tính điểm dựa theo ngày trong tuần
        int point = switch (today.getDayOfWeek()) {
            case MONDAY -> 10;
            case FRIDAY -> 15;
            default -> 5;
        };
        rewardPoint.setPoint(point);
        rewardPointRepository.save(rewardPoint);
        attendanceResponse = new AttendanceResponse();
        attendanceResponse.setPoint((double) point);
        attendanceResponse.setDate(Date.valueOf(today));

        return attendanceResponse;
    }

    @Override
    public DataListResponse<AttendanceResponse> getHistoryAttendance() {
        DataListResponse<AttendanceResponse> historyAttendance = null;
        User user = SecurityUtils.getUserFromPrincipal(userRepository);
        if (user == null) return historyAttendance;
        Integer userId = user.getId();

        List<RewardPoint> rewardPoints = rewardPointRepository.findAllByUserId(userId);
        if (rewardPoints.isEmpty()) return historyAttendance;
        historyAttendance = new DataListResponse<>();
        List<AttendanceResponse> attendanceResponses = rewardPoints.stream().map(rewardPointMapper::toAttendanceResponse).toList();
        historyAttendance.setData(attendanceResponses);
        return historyAttendance;
    }

    @Override
    public RewardPointResponse getRedeemReward() {
        RewardPointResponse rewardPointResponse = new RewardPointResponse();
        User user = SecurityUtils.getUserFromPrincipal(userRepository);
        if (user == null) return rewardPointResponse;
        Integer userId = user.getId();
        rewardPointResponse.setDateAttendanceContinuous(calculateDayAttendanceContinuous(userId));
        rewardPointResponse.setTotalPoint(getPoint());
        return rewardPointResponse;
    }

    private Integer calculateDayAttendanceContinuous(Integer userId) {
        List<RewardPoint> rewardPointList = rewardPointRepository.findAllByUserId(userId);
        if (rewardPointList.isEmpty()) return 0;
        int dayContinuous = 0;
        LocalDate today = LocalDate.now();
        for (RewardPoint rewardPoint : rewardPointList) {
            LocalDate date = rewardPoint.getDate().toLocalDate();
            if (date.isEqual(today.minusDays(1))) {
                today = date;
                dayContinuous++;
            } else {
                break;
            }
        }
        return dayContinuous;
    }

    @Override
    public Double getPoint() {
        User user = SecurityUtils.getUserFromPrincipal(userRepository);
        if (user == null) return 0.0;
        double totalScore = rewardPointRepository.sumPointByUserId(user.getId()).orElse(0.0);
        double totalScoreExchange = redeemRewardRepository.sumPointByUserId(user.getId()).orElse(0.0);
        return totalScore - totalScoreExchange;
    }
}
