package com.commic.v1.services.rating;

import com.commic.v1.dto.RatingDTO;
import com.commic.v1.entities.Rating;
import com.commic.v1.mapper.RatingMapper;
import com.commic.v1.repositories.IRatingRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingService implements IRatingService {
    @Autowired
    IRatingRepository ratingRepository;

    private final RatingMapper ratingMapper;
    @Override
    public void deleteByChapterId(Integer id) {
        List<Rating> list = ratingRepository.findByChapterId(id);
        list.forEach(rating -> rating.setIsDeleted(true));
        ratingRepository.saveAll(list);
    }

    @Override
    public List<Rating> findByChapterId(Integer id) {
        return ratingRepository.findByChapterId(id);
    }

    @Override
    public List<RatingDTO> findAllByUserId(Integer userId) {
        return ratingRepository.findAllByUserId(userId).stream()
                .map(ratingMapper::toDTO)
                .collect(Collectors.toList());
    }
}
