package com.commic.v1.mapper;

import com.commic.v1.dto.CommentDTO;
import com.commic.v1.dto.requests.CommentCreationRequestDTO;
import com.commic.v1.dto.responses.CommentCreationResponseDTO;
import com.commic.v1.entities.Comment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment toComment(CommentCreationRequestDTO comment);
    CommentCreationResponseDTO toCommentCreationRequestDTO(Comment comment);
    List<CommentDTO> toCommentDTOs(List<Comment> comments);
}
