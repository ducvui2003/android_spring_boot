package com.commic.v1.api;

import com.commic.v1.dto.requests.ChangePasswordRequest;
import com.commic.v1.dto.requests.ForgotPasswordRequest;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.UserResponse;
import com.commic.v1.jwt.JwtTokenUtil;
import com.commic.v1.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @GetMapping("/info")
    public ResponseEntity<UserResponse> getUserInfo(@RequestParam("token")  String token) {
        UserResponse user = userService.getUserInfo(jwtTokenUtil.extractUsername(token));
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<APIResponse<Void>> forgotPassword(@RequestBody @Validated ForgotPasswordRequest passwordRequest) {
        APIResponse<Void> response = userService.forgotPassword(passwordRequest.getEmail());
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<APIResponse<Void>> changePassword(@RequestBody @Validated ChangePasswordRequest passwordRequest) {
        APIResponse<Void> response = userService.changePassword(passwordRequest);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
