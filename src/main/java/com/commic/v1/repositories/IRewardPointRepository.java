package com.commic.v1.repositories;

import com.commic.v1.entities.RewardPoint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;

public interface IRewardPointRepository extends JpaRepository<RewardPoint, Integer> {
    boolean existsByUserIdAndDate(Integer userId, Date date);
}
