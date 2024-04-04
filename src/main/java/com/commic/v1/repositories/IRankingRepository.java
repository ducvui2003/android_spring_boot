package com.commic.v1.repositories;

import com.commic.v1.dto.responses.RankVoteResponseDTO;
import com.commic.v1.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IRankingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT r.chapter FROM Rating r")
    List<RankVoteResponseDTO> getRankingVote(String order);

//    List<RankVoteResponseDTO> getRankingView(String order);
}
