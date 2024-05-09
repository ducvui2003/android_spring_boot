package com.commic.v1.services.user;

import com.commic.v1.dto.UserDTO;
import com.commic.v1.dto.requests.ChangePasswordRequest;
import com.commic.v1.dto.requests.UserRequest;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.UserResponse;
import com.commic.v1.entities.User;

import com.commic.v1.exception.ErrorCode;
import com.commic.v1.mapper.UserMapper;
import com.commic.v1.repositories.*;

import com.commic.v1.services.mail.IEmailService;
import com.commic.v1.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.Random;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IEmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IRewardPointRepository rewardPointRepository;
    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private IHistoryRepository historyRepository;
    @Autowired
    private IRatingRepository ratingRepository;

    @Override
    public APIResponse<Void> forgotPassword(String email) {
        // Generate OTP
        String otp = generateOTP();

        // Create a response object
        APIResponse<Void> response = new APIResponse<>();

        // Find the user by email
        User user = userRepository.findByEmail(email).orElse(null);

        // If user is not found, set appropriate response message and code
        if (user == null) {
            response.setMessage("Email not found");
            response.setCode(HttpStatus.NOT_FOUND.value());
        } else {
            // If user is found, set OTP, save user, send email, and set appropriate response message and code
            user.setOtp(otp);
            userRepository.save(user);
            emailService.sendMailOTP(email, otp);
            response.setMessage("Send OTP successfully");
            response.setCode(HttpStatus.CREATED.value());
        }
        return response;
    }

    /**
     * Changes the password of a user. The method supports two scenarios:
     * 1. Changing password with an OTP: In this case, the OTP and email are used to identify the user.
     * 2. Changing password without an OTP: In this case, the current password is used for authentication.
     *
     * @param passwordRequest The request containing the new password, OTP (if available), and other necessary information.
     * @return An APIResponse<Void> indicating the result of the operation.
     */
    @Override
    public APIResponse<Void> changePassword(ChangePasswordRequest passwordRequest) {
        APIResponse<Void> response = new APIResponse<>();
        String otp = passwordRequest.getOtp();
        String newPassword = passwordRequest.getNewPassword();

        // Check if the new password matches the confirmed password
        if (!newPassword.equals(passwordRequest.getConfirmPassword())) {
            response.setMessage("Password is not equal confirm password");
            response.setCode(HttpStatus.BAD_REQUEST.value());
            return response;
        }

        User user;
        if (!StringUtils.hasText(otp)) {
            // If OTP is not provided, authenticate the user with the current password
            user = SecurityUtils.getUserFromPrincipal(userRepository);
            if (user == null || !passwordEncoder.matches(passwordRequest.getPassword(), user.getPassword())) {
                response.setMessage(user == null ? "User not found" : "Password is incorrect");
                response.setCode(HttpStatus.BAD_REQUEST.value());
                return response;
            }
        } else {
            // If OTP is provided, find the user by email and OTP
            user = userRepository.findByEmailAndOtp(passwordRequest.getEmail(), otp).orElse(null);
            if (user == null) {
                response.setMessage("User not found");
                response.setCode(HttpStatus.NOT_FOUND.value());
                return response;
            }
        }

        // At this point, the user is authenticated. Proceed to change the password.
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        response.setMessage("Password changed successfully");
        response.setCode(HttpStatus.OK.value());
        return response;
    }

    @Override
    public APIResponse<Void> register(UserDTO userDTO) {
        APIResponse<Void> response = new APIResponse<>();
        try {
            /*Kiểm tra xem có tên người dùng hoặc email đã có người đăng ký hay chưa
            * Nếu chưa có thì mới cho phép đăng ký
            * */
            Optional<User> userByName = userRepository.findByUsername(userDTO.getUsername());
            Optional<User> userByEmail = userRepository.findByEmail(userDTO.getEmail());
            if (userByName.isEmpty() && userByEmail.isEmpty()) {
                userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                User user = userMapper.toUserResponseEntity(userDTO);
                userRepository.save(user);
                response.setMessage("Register success");
                response.setCode(HttpStatus.OK.value());
                return response;
            } else {
                response.setMessage("Username or Email already exists");
                response.setCode(ErrorCode.CREATE_FAILED.getCode());
                return response;
            }
        } catch(Exception ex){
            response.setMessage("Register failed, check your registration information again");
            response.setCode(ErrorCode.CREATE_FAILED.getCode());
            return response;
        }
    }

    public UserResponse getUserInfo(String username) {
        // Find the user by username. If the user is not found, return null.
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }

        // Map the User entity to UserResponse DTO
        UserResponse result = userMapper.toDTO(user);

        // Calculate the total reward points of the user by summing up all the points
        result.setRewardPoint(rewardPointRepository.sumPointByUser(user).orElse(0));
        // Set the total attendance dates of the user by counting the size of the reward points
        result.setTotalAttendanceDates(rewardPointRepository.countByUser(user).orElse(0));
        // Set the total books read by the user by counting the size of the histories
        result.setTotalBookReads(historyRepository.countDistinctByUser(user).orElse(0));
        // Set the total comments made by the user by counting the size of the comments
        result.setTotalComments(commentRepository.countByUser(user).orElse(0));
        // Set the total ratings made by the user by counting the size of the ratings
        result.setNumberOfRatings(ratingRepository.countByUser(user).orElse(0));

        // Return the result
        return result;
    }

    @Override
    public boolean updateInfo(UserRequest userRequest) {
        // Retrieve the currently authenticated user using the SecurityUtils helper class
        User user = SecurityUtils.getUserFromPrincipal(userRepository);

        // If the user is not found (i.e., not authenticated), return false
        if (user == null) {
            return false;
        }

        // Update the user's phone number and full name with the information from the request
        user.setPhone(userRequest.getPhone());
        user.setFullName(userRequest.getFullName());

        // Save the updated user information in the repository
        userRepository.save(user);

        // If the operation reaches this point without any exceptions, return true indicating success
        return true;
    }


    private String generateOTP() {
        int ranNum = new Random().nextInt((999999 - 100000) + 1) + 100000;
        return String.valueOf(ranNum);
    }
}
