package com.commic.v1.api.user;

import com.commic.v1.dto.RatingDTO;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.entities.Rating;
import com.commic.v1.services.rating.IRatingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingController {

    private final IRatingService ratingService;

    @Autowired
    public RatingController(IRatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<APIResponse<Void>> createRating(@RequestBody @Valid RatingDTO ratingDTO) {
        APIResponse<Void> response = ratingService.createRating(ratingDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PutMapping
    public ResponseEntity<APIResponse<Void>> updateRating(@RequestBody @Valid RatingDTO ratingDTO) {
        if (ratingDTO.getId() == null) {
            return ResponseEntity.status(400).body(new APIResponse<>(400, "Id is required", null));
        }
        APIResponse<Void> response = ratingService.updateRating(ratingDTO);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @GetMapping("/user/{userId}")
    public APIResponse<List<RatingDTO>> getAllRatingsByUserId(@PathVariable Integer userId) {
        List<RatingDTO> ratings = ratingService.findAllByUserId(userId);
        return new APIResponse<>(200, "Success", ratings);
    }

    @GetMapping("/chapter/{chapterId}")
    public APIResponse<RatingDTO> getRatingsByChapterId(@PathVariable Integer chapterId) {
        RatingDTO rating = ratingService.findRatingByChapterId(chapterId).getResult();
        if (rating == null) {
            return new APIResponse<>(404, "Not found", null);
        } else {
            return new APIResponse<>(200, "Success", rating);
        }
    }
}
