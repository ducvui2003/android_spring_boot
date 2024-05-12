package com.commic.v1.api;

import com.commic.v1.dto.CommentDTO;
import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.requests.CommentCreationRequestDTO;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.CommentCreationResponseDTO;
import com.commic.v1.dto.responses.CommentResponseDTO;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.services.comment.CommentConst;
import com.commic.v1.services.comment.IAdminCommentServices;
import com.commic.v1.services.comment.ICommentServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public List<CommentDTO> getComment(@PathVariable("idChapter") Integer idChapter) {
        return commentServices.getComment(idChapter);
    }

    @Autowired
    IAdminCommentServices commentAdminServices;

    @GetMapping()
    public APIResponse<DataListResponse<CommentResponseDTO>> getListComment(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        APIResponse<DataListResponse<CommentResponseDTO>> response = new APIResponse<>();
        Pageable pageable = PageRequest.of(page - 1, size);
        DataListResponse<CommentResponseDTO> result = commentAdminServices.get(pageable);
        response.setResult(result);
        return response;
    }
}
