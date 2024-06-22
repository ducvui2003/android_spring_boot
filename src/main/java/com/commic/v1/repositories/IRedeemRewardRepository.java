package com.commic.v1.repositories;

import com.commic.v1.entities.Item;
import com.commic.v1.entities.RedeemReward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.Optional;

@Repository
public interface IRedeemRewardRepository extends JpaRepository<RedeemReward, Integer> {
    boolean existsByUserIdAndItemId(Integer userId, Integer itemId);

    @Query("SELECT SUM(rr.item.point) FROM RedeemReward rr WHERE rr.user.id = :userId")
    Optional<Double> sumPointByUserId(Integer userId);

    @Query("SELECT rr.item FROM RedeemReward rr WHERE rr.user.id = :userId")
    Page<Item> getByUserId(Integer userId, Pageable pageable);

    @Query("SELECT rr.date FROM RedeemReward rr WHERE rr.user.id = :userId AND rr.item.id = :itemId")
    Date findDateByUserIdAndItemId(@Param("userId") Integer userId,@Param("itemId") Integer itemId);
}
