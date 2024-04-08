package com.commic.v1.repositories;

import com.commic.v1.entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findAllByBookId(Integer id);
    long countByBookId(Integer id);
    @Query("SELECT AVG(rating.star) FROM Chapter chapter JOIN chapter.ratings rating WHERE chapter.book.id = :id")
    Double countStarAvgByBookId(Integer id);
}
