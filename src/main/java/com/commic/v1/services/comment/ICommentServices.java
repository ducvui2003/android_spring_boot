package com.commic.v1.services.comment;

import com.commic.v1.dto.requests.CommentCreationRequestDTO;
import com.commic.v1.dto.responses.CommentCreationResponseDTO;

public interface ICommentServices {
    CommentCreationResponseDTO create(CommentCreationRequestDTO requestDTO);
}
