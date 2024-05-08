package com.commic.v1.repositories;

import com.commic.v1.entities.History;
import com.commic.v1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IHistoryRepository extends JpaRepository<History, Long> {
    @Query("SELECT COUNT(DISTINCT h) FROM History h WHERE h.user = :user")
    Optional<Integer> countDistinctByUser(@Param("user") User user);

}
