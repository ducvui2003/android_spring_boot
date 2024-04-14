package com.commic.v1.exception;

import com.commic.v1.dto.responses.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
//    @ExceptionHandler (value = Exception.class)
//    public ResponseEntity<APIResponse> handleException(RuntimeException e) {
//        APIResponse apiResponse = new APIResponse();
//        apiResponse.setCode(ErrorCode.INTERNAL_SERVER_ERROR.getCode());
//        apiResponse.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
//        return ResponseEntity.badRequest().body(apiResponse);
//    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<APIResponse> handleAppException(AppException e) {
        APIResponse apiResponse = new APIResponse();
        apiResponse.setCode(e.getErrorCode().getCode());
        apiResponse.setMessage(e.getErrorCode().getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse> handleValidationException(MethodArgumentNotValidException e) {
        APIResponse apiResponse = new APIResponse();
        String enumKey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
