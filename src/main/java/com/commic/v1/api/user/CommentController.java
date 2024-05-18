package com.commic.v1.api.user;

import com.commic.v1.dto.CommentDTO;
import com.commic.v1.dto.requests.CommentCreationRequestDTO;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.CommentCreationResponseDTO;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.services.comment.ICommentServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    ICommentServices commentServices;

    @PostMapping
    @ResponseBody
    public APIResponse<CommentCreationResponseDTO> create(@RequestBody @Valid CommentCreationRequestDTO requestDTO) {
        APIResponse<CommentCreationResponseDTO> apiResponse = new APIResponse<>();
        apiResponse.setCode(ErrorCode.CREATE_SUCCESS.getCode());
        apiResponse.setMessage(ErrorCode.CREATE_SUCCESS.getMessage());
        CommentCreationResponseDTO result = commentServices.create(requestDTO);
        apiResponse.setResult(result);
        return apiResponse;
    }

    @GetMapping("/chapter/{idChapter}")
    public APIResponse<List<CommentDTO>> getComment(@PathVariable("idChapter") Integer idChapter) {
        APIResponse<List<CommentDTO>> apiResponse = new APIResponse<>();
        List<CommentDTO> comments = commentServices.getComment(idChapter);
        apiResponse.setCode(ErrorCode.FOUND.getCode());
        apiResponse.setMessage(ErrorCode.FOUND.getMessage());
        apiResponse.setResult(comments);
        return apiResponse;
    }


}
