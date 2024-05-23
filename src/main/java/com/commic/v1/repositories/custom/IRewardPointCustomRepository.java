package com.commic.v1.repositories.custom;

import com.commic.v1.entities.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IRewardPointCustomRepository {
    Optional<Integer> getDayAttendanceContinuous(User user);
}
