package com.commic.v1.api.admin;

import com.commic.v1.dto.CommentDTO;
import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.CommentResponseDTO;
import com.commic.v1.services.comment.CommentConst;
import com.commic.v1.services.comment.IAdminCommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/admin/comment")
public class AdminCommentController {
    @Autowired
    IAdminCommentServices commentAdminServices;

    @GetMapping
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
