package com.commic.v1.services.attendance;

import com.commic.v1.entities.RewardPoint;
import com.commic.v1.entities.User;
import com.commic.v1.repositories.IRewardPointRepository;
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

    @Override
    public boolean createAttendance(Integer userId) {
//        Kiểm tra xem ngày hôm nay đã điểm danh chưa
        LocalDate today = LocalDate.now();
        boolean isAttendance = rewardPointRepository.existsByUserIdAndDate(userId, Date.valueOf(today));
        //        Nếu đã điểm danh thì trả về false
        if (isAttendance) return false;
        RewardPoint rewardPoint = new RewardPoint();
        rewardPoint.setUser(User.builder().id(userId).build());
        rewardPoint.setDate(Date.valueOf(today));
        int point = switch (today.getDayOfWeek()) {
            case MONDAY -> 10;
            case FRIDAY -> 15;
            default -> 5;
        };
        rewardPoint.setPoint(point);
        rewardPointRepository.save(rewardPoint);
        return true;
    }
}
