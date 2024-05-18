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
    Page<Comment> findAllByStateOrderByCreatedAtDesc(Integer state, Pageable pageable);

    List<Comment> findByChapterId(Integer id);
  
    Optional<Integer> countByUser(User user);
}
