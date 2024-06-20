package com.commic.v1.services.rating;

import com.commic.v1.dto.RatingDTO;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.entities.Rating;

import java.util.List;

public interface IRatingService {
    void deleteByChapterId(Integer id);

    List<Rating> findByChapterId(Integer id);

    List<RatingDTO> findAllByUserId(Integer userId);

    APIResponse<Void> createRating(RatingDTO ratingDTO);
    APIResponse<Void> updateRating(RatingDTO ratingDTO);

    APIResponse<RatingDTO> findRatingByChapterId(Integer id);
    Integer countAllRating();
    Integer countAllRatingByBookId(Integer chapterId);
}
