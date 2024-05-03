package com.commic.v1.repositories;

import com.commic.v1.entities.Comment;
import com.commic.v1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Integer> {

    Optional<Integer> countByUser(User user);
}
