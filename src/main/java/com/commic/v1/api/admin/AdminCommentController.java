package com.commic.v1.api.admin;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.CommentDTOResponse;
import com.commic.v1.services.comment.CommentConst;
import com.commic.v1.services.comment.IAdminCommentServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/v1/admin/comment")
public class AdminCommentController {
    @Autowired
    IAdminCommentServices commentAdminServices;

    @GetMapping("/get")
    public APIResponse<DataListResponse<CommentDTOResponse>> getListComment(
            @RequestParam(name = "page") Integer page,
            @RequestParam(name = "size") Integer size,
            @RequestParam(name = "state") String state
    ) {
        APIResponse<DataListResponse<CommentDTOResponse>> response = new APIResponse<>();
        Pageable pageable = PageRequest.of(page, size);
        CommentConst commentConst;
        try {
            commentConst = CommentConst.valueOf(state.toUpperCase());
        } catch (IllegalArgumentException e) {
            response.setCode(400);
            response.setMessage("State not found");
            return response;
        }
        DataListResponse<CommentDTOResponse> result = commentAdminServices.get(commentConst, pageable);
        response.setResult(result);
        return response;
    }
}
