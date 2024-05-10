package com.commic.v1.repositories;

import com.commic.v1.entities.Comment;
import com.commic.v1.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface ICommentRepository extends JpaRepository<Comment, Integer> {
    Page<Comment> findAllByStateOrderByCreatedAtDesc(String value, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.chapter.id = :idChapter")
    List<Comment> findAllByChapterId(Integer idChapter);
  
    Optional<Integer> countByUser(User user);
}
