package com.commic.v1.services.rating;

import com.commic.v1.dto.RatingDTO;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.entities.Rating;
import com.commic.v1.entities.User;
import com.commic.v1.mapper.RatingMapper;
import com.commic.v1.repositories.IRatingRepository;
import com.commic.v1.repositories.IUserRepository;
import com.commic.v1.util.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RatingService implements IRatingService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IRatingRepository ratingRepository;
    @Autowired
    RatingMapper ratingMapper;

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
        return ratingRepository.findAllByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(ratingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public APIResponse<Void> createRating(RatingDTO ratingDTO) {

        User user = SecurityUtils.getUserFromPrincipal(userRepository);

        // Validate the input
        if (ratingDTO == null) {
            return new APIResponse<>(HttpStatus.BAD_REQUEST.value(), "Rating data is null", null);
        }

        if (ratingDTO.getChapterId() == null || ratingDTO.getUserId() == null || ratingDTO.getStar() == null) {
            return new APIResponse<>(HttpStatus.BAD_REQUEST.value(), "Required fields are missing", null);
        }

        if (ratingDTO.getStar() < 1.0 || ratingDTO.getStar() > 5.0) {
            return new APIResponse<>(HttpStatus.BAD_REQUEST.value(), "Invalid rating value: must be between 1 and 5", null);
        }

        try {
            Rating rating = ratingMapper.toEntity(ratingDTO);
            rating.setUser(user);
            ratingRepository.save(rating);
            return new APIResponse<>(HttpStatus.NO_CONTENT.value(), "Create rate successfully", null);
        } catch (DataIntegrityViolationException ex) {
            // Handle specific exception related to data integrity
            ex.printStackTrace();
            return new APIResponse<>(HttpStatus.CONFLICT.value(), "Data integrity violation: " + ex.getMessage(), null);
        } catch (DataAccessException ex) {
            // Handle generic database exceptions
            ex.printStackTrace();
            return new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Database error: " + ex.getMessage(), null);
        } catch (RuntimeException ex) {
            // Handle other runtime exceptions
            ex.printStackTrace();
            return new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Create rate failed", null);
        }
    }


    @Override
    public APIResponse<Void> updateRating(RatingDTO ratingDTO) {
        User user = SecurityUtils.getUserFromPrincipal(userRepository);
        try {
            // Tìm đối tượng Rating tương ứng trong cơ sở dữ liệu dựa trên id
            Rating rating = ratingRepository.findById(Long.valueOf(ratingDTO.getId())).orElse(null);

            // Kiểm tra xem đối tượng Rating có tồn tại không
            if (rating == null) {
                // Nếu không tìm thấy, trả về thông báo lỗi
                return new APIResponse<>(HttpStatus.NOT_FOUND.value(), "Rating not found", null);
            }

            rating = ratingMapper.toEntity(ratingDTO);
            rating.setUser(user);
            rating.setStar(ratingDTO.getStar());
            rating.setCreatedAt(ratingDTO.getCreatedAt());

            // Lưu thay đổi vào cơ sở dữ liệu
            ratingRepository.save(rating);

            // Trả về thông báo thành công
            return new APIResponse<>(HttpStatus.NO_CONTENT.value(), "Update rate successfully", null);
        } catch (Exception ex) {
            // Nếu xảy ra ngoại lệ, in thông báo lỗi và trả về mã lỗi 500 (INTERNAL_SERVER_ERROR)
            ex.printStackTrace();
            return new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Update rate failed", null);
        }
    }

    @Override
    public APIResponse<RatingDTO> findRatingByChapterId(Integer id) {
        try {
            // Tìm đối tượng Rating tương ứng trong cơ sở dữ liệu dựa trên id
            Rating rating = ratingRepository.findRatingByChapterId(id).orElse(null);

            // Kiểm tra xem đối tượng Rating có tồn tại không
            if (rating == null) {
                // Nếu không tìm thấy, trả về thông báo lỗi
                return new APIResponse<>(HttpStatus.NOT_FOUND.value(), "Rating not found", null);
            }

            // Chuyển đối tượng Rating thành đối tượng DTO và trả về thông báo thành công
            return new APIResponse<>(HttpStatus.OK.value(), "Success", ratingMapper.toDTO(rating));
        } catch (Exception ex) {
            // Nếu xảy ra ngoại lệ, in thông báo lỗi và trả về mã lỗi 500 (INTERNAL_SERVER_ERROR)
            ex.printStackTrace();
            return new APIResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Get rate failed", null);
        }
    }
}
