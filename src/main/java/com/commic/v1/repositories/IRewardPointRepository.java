package com.commic.v1.repositories;

import com.commic.v1.entities.RewardPoint;
import com.commic.v1.entities.User;
import com.commic.v1.repositories.custom.IRewardPointCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.Optional;

public interface IRewardPointRepository extends JpaRepository<RewardPoint, Integer> {
    boolean existsByUserIdAndDate(Integer userId, Date date);

    @Query("SELECT SUM(r.point) FROM RewardPoint r WHERE r.user = ?1")
    Optional<Integer> sumPointByUser(User user);

    Optional<Integer> countByUser(User user);

    @Query("SELECT SUM(r.point) FROM RewardPoint r WHERE r.user.id = :userId")
    Optional<Integer> sumPointByUserId(@Param("userId") Integer userId);
}
