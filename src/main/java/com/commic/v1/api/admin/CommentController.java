package com.commic.v1.api.admin;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.requests.CommentChangeDTO;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.CommentResponseDTO;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.services.comment.ICommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController("CommentControllerAdmin")
@RequestMapping("/api/v1/admin/comment")
public class CommentController {
    @Autowired
    ICommentServices commentAdminServices;

    @GetMapping()
    public APIResponse<DataListResponse<CommentResponseDTO>> getListComment(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        APIResponse<DataListResponse<CommentResponseDTO>> apiResponse = new APIResponse<>();
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("createdAt").descending());
        DataListResponse<CommentResponseDTO> items = commentAdminServices.getComments(pageable);
        if (items.getData().isEmpty()) {
            apiResponse.setCode(ErrorCode.NOT_FOUND.getCode());
            apiResponse.setMessage(ErrorCode.NOT_FOUND.getMessage());

        } else {
            apiResponse.setCode(ErrorCode.FOUND.getCode());
            apiResponse.setMessage(ErrorCode.FOUND.getMessage());
            apiResponse.setResult(items);
        }
        return apiResponse;
    }


    //    1: Hiện
//    2: Ẩn
    @PostMapping()
    public APIResponse changeState(@RequestBody CommentChangeDTO commentChangeDTO) {
        APIResponse apiResponse = new APIResponse();
        boolean isSuccess = commentAdminServices.changeState(commentChangeDTO.getCommentId(), commentChangeDTO.getState());
        if (isSuccess) {
            apiResponse.setCode(ErrorCode.UPDATE_SUCCESS.getCode());
            apiResponse.setMessage(ErrorCode.UPDATE_SUCCESS.getMessage());
        } else {
            apiResponse.setCode(ErrorCode.UPDATE_FAILED.getCode());
            apiResponse.setMessage(ErrorCode.UPDATE_FAILED.getMessage());
        }
        return apiResponse;
    }

    @GetMapping("/detail/{commentId}")
    public APIResponse getDetailComment(@PathVariable Integer commentId) {
        APIResponse<CommentResponseDTO> apiResponse = new APIResponse();
        CommentResponseDTO commentDTO = commentAdminServices.getCommentDetail(commentId);
        if (commentDTO != null) {
            apiResponse.setCode(ErrorCode.FOUND.getCode());
            apiResponse.setMessage(ErrorCode.FOUND.getMessage());
            apiResponse.setResult(commentDTO);
        } else {
            apiResponse.setCode(ErrorCode.NOT_FOUND.getCode());
            apiResponse.setMessage(ErrorCode.NOT_FOUND.getMessage());
        }
        return apiResponse;
    }
}