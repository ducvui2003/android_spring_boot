package com.commic.v1.services.comment;

import com.commic.v1.dto.CommentDTO;
import com.commic.v1.dto.requests.CommentCreationRequestDTO;
import com.commic.v1.dto.responses.CommentCreationResponseDTO;

import java.util.List;

public interface ICommentServices {
    CommentCreationResponseDTO create(CommentCreationRequestDTO requestDTO);

    List<CommentDTO> getComment(Integer idChapter);

    void deleteByChapterId(Integer id);
}
