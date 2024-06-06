package com.commic.v1.services.comment;

import com.commic.v1.dto.CommentDTO;
import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.requests.CommentCreationRequestDTO;
import com.commic.v1.dto.responses.CommentCreationResponseDTO;
import com.commic.v1.dto.responses.CommentResponseDTO;

import java.util.List;

public interface ICommentServices {
    CommentCreationResponseDTO create(CommentCreationRequestDTO requestDTO);

    List<CommentDTO> getComment(Integer idChapter);

    void deleteByChapterId(Integer id);

    DataListResponse<CommentResponseDTO> getComments(Pageable pageable);

    boolean changeState(Integer commentId, Integer state);

    CommentResponseDTO getCommentDetail(Integer commentId);

    DataListResponse<CommentResponseDTO> getComments(CommentGetType commentGetType , Integer id, Pageable pageable);
}
