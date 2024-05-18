package com.commic.v1.services.comment;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.CommentDTOResponse;
import com.commic.v1.dto.responses.CommentResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface IAdminCommentServices {
    public DataListResponse<CommentDTOResponse> get(CommentConst state, Pageable pageable);
    DataListResponse<CommentResponseDTO> get(Pageable pageable);
}
