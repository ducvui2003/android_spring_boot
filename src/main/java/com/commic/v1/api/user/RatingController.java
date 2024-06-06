package com.commic.v1.api.user;

import com.commic.v1.dto.RatingDTO;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.entities.Rating;
import com.commic.v1.services.rating.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    private final IRatingService ratingService;

    @Autowired
    public RatingController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/user/{userId}")
    public APIResponse<List<RatingDTO>> getAllRatingsByUserId(@PathVariable Integer userId) {
        List<RatingDTO> ratings = ratingService.findAllByUserId(userId);
        return new APIResponse<>(200, "Success", ratings);
    }
}
