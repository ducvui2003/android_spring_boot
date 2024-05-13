package com.commic.v1.mapper;

import com.commic.v1.dto.CommentDTO;
import com.commic.v1.dto.requests.CommentCreationRequestDTO;
import com.commic.v1.dto.responses.CommentCreationResponseDTO;
import com.commic.v1.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toComment(CommentCreationRequestDTO comment);
    CommentCreationResponseDTO toCommentCreationRequestDTO(Comment comment);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "chapter.id", target = "chapterId")
    CommentDTO toCommentDTOs(Comment comment);
}
