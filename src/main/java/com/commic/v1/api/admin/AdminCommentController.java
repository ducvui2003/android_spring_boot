package com.commic.v1.api.admin;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.CommentResponseDTO;
import com.commic.v1.services.comment.IAdminCommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/admin/comment")
public class AdminCommentController {
    @Autowired
    IAdminCommentServices commentAdminServices;

    @GetMapping()
    public APIResponse<DataListResponse<CommentResponseDTO>> getListComment(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size
    ) {
        APIResponse<DataListResponse<CommentResponseDTO>> response = new APIResponse<>();
        Pageable pageable = PageRequest.of(page, size,  Sort.by("createdAt").descending());
        DataListResponse<CommentResponseDTO> result = commentAdminServices.getComments(pageable);
        response.setResult(result);
        return response;
    }
}
