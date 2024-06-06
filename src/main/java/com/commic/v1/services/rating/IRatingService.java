package com.commic.v1.services.rating;

import com.commic.v1.dto.RatingDTO;
import com.commic.v1.entities.Rating;

import java.util.List;

public interface IRatingService {
    void deleteByChapterId(Integer id);

    List<Rating> findByChapterId(Integer id);

    List<RatingDTO> findAllByUserId(Integer userId);
}
