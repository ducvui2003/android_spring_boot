package com.commic.v1.exception;

import com.commic.v1.dto.responses.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<APIResponse> handleAppException(AppException e) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setCode(e.getErrorCode().getCode());
        apiResponse.setMessage(e.getErrorCode().getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
