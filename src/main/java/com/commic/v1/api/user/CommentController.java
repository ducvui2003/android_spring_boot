package com.commic.v1.api.user;

import com.commic.v1.dto.CommentDTO;
import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.requests.CommentCreationRequestDTO;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.CommentCreationResponseDTO;
import com.commic.v1.dto.responses.CommentResponseDTO;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.services.comment.CommentGetType;
import com.commic.v1.services.comment.ICommentServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    ICommentServices commentServices;

    //    Comment chapter của 1 truyện
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

    //    Lấy ra tất cả comment của 1 chapter
    @GetMapping("/chapter/{idChapter}")
    public APIResponse<List<CommentDTO>> getComment(@PathVariable("idChapter") Integer idChapter) {
        APIResponse<List<CommentDTO>> apiResponse = new APIResponse<>();
        List<CommentDTO> comments = commentServices.getComment(idChapter);
        apiResponse.setCode(ErrorCode.FOUND.getCode());
        apiResponse.setMessage(ErrorCode.FOUND.getMessage());
        apiResponse.setResult(comments);
        return apiResponse;
    }

    @GetMapping("/chapter")
    public APIResponse<DataListResponse<CommentResponseDTO>> getComment(
            @RequestParam(name = "type", defaultValue = "book") String type,
            @RequestParam(name = "id", required = false) Integer id,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        CommentGetType commentGetType = CommentGetType.valueOf(type.toUpperCase());
        APIResponse<DataListResponse<CommentResponseDTO>> apiResponse = new APIResponse<>();
        DataListResponse<CommentResponseDTO> comments = commentServices.getComments(commentGetType, id, pageable);
        apiResponse.setCode(ErrorCode.FOUND.getCode());
        apiResponse.setMessage(ErrorCode.FOUND.getMessage());
        apiResponse.setResult(comments);
        return apiResponse;
    }
}
