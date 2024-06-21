package com.commic.v1.api.admin;

import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.UserResponse;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "UserControllerAdmin")
@RequestMapping("/api/v1/admin/users")
public class UserController {
    @Autowired
    private IUserService userService;
    @GetMapping()
    public ResponseEntity<APIResponse<List<UserResponse>>> findAll() {
        APIResponse<List<UserResponse>> response = new APIResponse<>();
        response.setResult(userService.findAll().getResult());
        response.setCode(ErrorCode.FOUND.getCode());
        response.setMessage(ErrorCode.NOT_FOUND.getMessage());
        return ResponseEntity.status(response.getCode()).body(response);
    }
    @PostMapping("/block/{id}")
    public ResponseEntity<APIResponse<Void>> blockUser(@PathVariable("id") Integer id) {
        APIResponse<Void> response = userService.blockUser(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }

    @PostMapping("/unblock/{id}")
    public ResponseEntity<APIResponse<Void>> unblockUser(@PathVariable("id") Integer id){
        APIResponse<Void> response = userService.unblockUser(id);
        return ResponseEntity.status(response.getCode()).body(response);
    }
}
