package com.commic.v1.repositories;

import com.commic.v1.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Integer> {
    Page<Comment> findAllByStateOrderByCreatedAtDesc(String value, Pageable pageable);

    Optional<Integer> countByUser(User user);
}
