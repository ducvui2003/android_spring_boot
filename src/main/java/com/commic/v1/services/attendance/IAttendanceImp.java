package com.commic.v1.services.attendance;

import com.commic.v1.dto.responses.AttendanceResponse;
import com.commic.v1.entities.RewardPoint;
import com.commic.v1.entities.User;
import com.commic.v1.repositories.IRewardPointRepository;
import com.commic.v1.repositories.IUserRepository;
import com.commic.v1.repositories.custom.IRewardPointCustomRepository;
import com.commic.v1.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

//monday +10d
//friday +15d
@Service
public class IAttendanceImp implements IAttendanceServices {
    @Autowired
    IRewardPointRepository rewardPointRepository;
    @Autowired
    IRewardPointCustomRepository rewardPointCustomRepository;
    @Autowired
    IUserRepository userRepository;

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
        attendanceResponse.setDateAttendanceContinuous(rewardPointCustomRepository.getDayAttendanceContinuous(user).orElse(0));
        attendanceResponse.setTotalPoint(Double.valueOf(rewardPointRepository.sumPointByUserId(userId).orElse(0)));
        return attendanceResponse;
    }
}
