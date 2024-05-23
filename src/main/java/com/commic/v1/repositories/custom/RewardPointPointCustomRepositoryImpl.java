package com.commic.v1.repositories.custom;

import com.commic.v1.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RewardPointPointCustomRepositoryImpl implements IRewardPointCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Integer> getDayAttendanceContinuous(User user) {
        String sql = "SELECT ABS(DATEDIFF(CURDATE(), date)) FROM reward_points \n" +
                "WHERE id = \n" +
                "(SELECT MAX(id) as id\n" +
                "FROM reward_points\n" +
                "WHERE user_id = :userId )";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userId", user.getId());
        try {
            Long result = ((Number) query.getSingleResult()).longValue();
            if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
                throw new IllegalStateException("Result exceeds range of Integer: " + result);
            }
            return Optional.ofNullable(result.intValue());
        } catch (NoResultException e) {
            return Optional.empty(); // Handle case where no result is found
        }
    }
}
