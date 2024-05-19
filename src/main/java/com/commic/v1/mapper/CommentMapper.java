package com.commic.v1.mapper;

import com.commic.v1.dto.CommentDTO;
import com.commic.v1.dto.requests.CommentCreationRequestDTO;
import com.commic.v1.dto.responses.CommentCreationResponseDTO;
import com.commic.v1.dto.responses.CommentResponseDTO;
import com.commic.v1.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "chapterId", target = "chapter.id")
    Comment toComment(CommentCreationRequestDTO comment);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "chapter.book.name", target = "bookName")
    @Mapping(source = "chapter.name", target = "chapterNumber")
    CommentResponseDTO toCommentResponseDTO(Comment comment);

    CommentCreationResponseDTO toCommentCreationRequestDTO(Comment comment);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "chapter.id", target = "chapterId")
    CommentDTO toCommentDTOs(Comment comment);
}
