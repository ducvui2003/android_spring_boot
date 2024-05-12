package com.commic.v1.services.comment;

import com.commic.v1.dto.CommentDTO;
import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.CommentResponseDTO;
import org.springframework.data.domain.Pageable;

public interface IAdminCommentServices {
    DataListResponse<CommentResponseDTO> get(Pageable pageable);
}
