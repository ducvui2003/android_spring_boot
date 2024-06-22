package com.commic.v1.repositories;

import com.commic.v1.entities.Rating;
import com.commic.v1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRatingRepository extends JpaRepository<Rating, Long> {

    Optional<Integer> countByUser(User user);

    List<Rating> findByChapterId(Integer chapterId);

    List<Rating> findAllByUserIdOrderByCreatedAtDesc(Integer userId);

    Optional<Rating> findRatingByChapterId(Integer id);

    @Query("SELECT COUNT(rating) FROM Rating rating")
    Integer countAllRating();
    @Query("SELECT COUNT(r.star) FROM Book b JOIN b.chapters c JOIN c.ratings r WHERE b.id = :bookId")
    Integer countAllRatingByBookId(@Param("bookId") Integer bookId);
}
