package com.commic.v1.services.user;

import com.commic.v1.dto.requests.ChangePasswordRequest;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.UserResponse;
import com.commic.v1.entities.User;
import com.commic.v1.mapper.UserMapper;
import com.commic.v1.repositories.IUserRepository;
import com.commic.v1.services.mail.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    UserMapper userMapper;
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

    @Override
    public APIResponse<Void> changePassword(ChangePasswordRequest passwordRequest) {
        APIResponse<Void> response = new APIResponse<>();
        String email = passwordRequest.getEmail();
        String password = passwordRequest.getPassword();
        String otp = passwordRequest.getOtp();

        // Kiểm tra xem mật khẩu có khớp với mật khẩu xác nhận hay không
        if (!password.equals(passwordRequest.getConfirmPassword())) {
            response.setMessage("Password is not equal confirm password");
            response.setCode(HttpStatus.BAD_REQUEST.value());
            return response;
        }
        User usera = userRepository.findByOtp("133170").orElse(null);

        // Tìm người dùng theo email và mã OTP (nếu có)
        User user = StringUtils.hasText(otp) ? userRepository.findByEmailAndOtp(email, otp).orElse(null) :
                userRepository.findByEmail(email).orElse(null);

        // Kiểm tra xem người dùng có tồn tại không
        if (user == null) {
            response.setMessage("User not found");
            response.setCode(HttpStatus.NOT_FOUND.value());
            return response;
        }

        // Mã hóa mật khẩu mới và lưu lại
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        response.setMessage("Password changed successfully");
        response.setCode(HttpStatus.OK.value());

        return response;
    }

    @Override
    public UserResponse getUserInfo(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        return userMapper.toDTO(user);

    }


    private String generateOTP() {
        int ranNum = new Random().nextInt((999999 - 100000) + 1) + 100000;
        return String.valueOf(ranNum);
    }
}
