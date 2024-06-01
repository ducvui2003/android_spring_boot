package com.commic.v1.repositories;

import com.commic.v1.entities.Rating;
import com.commic.v1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, Long> {

    Optional<Integer> countByUser(User user);

    List<Rating> findByChapterId(Integer chapterId);

    List<Rating> findAllByUserId(Integer userId);


}
