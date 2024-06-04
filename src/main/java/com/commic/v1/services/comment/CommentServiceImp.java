package com.commic.v1.services.comment;

import com.commic.v1.dto.CommentDTO;
import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.requests.CommentCreationRequestDTO;
import com.commic.v1.dto.responses.CommentCreationResponseDTO;
import com.commic.v1.dto.responses.CommentResponseDTO;
import com.commic.v1.entities.Comment;
import com.commic.v1.entities.User;
import com.commic.v1.exception.AppException;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.mapper.CommentMapper;
import com.commic.v1.repositories.IBookRepository;
import com.commic.v1.repositories.IChapterRepository;
import com.commic.v1.repositories.ICommentRepository;
import com.commic.v1.repositories.IUserRepository;
import com.commic.v1.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImp implements ICommentServices {
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IChapterRepository chapterRepository;
    @Autowired
    ICommentRepository commentRepository;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    IBookRepository bookRepository;

    @Override
    public CommentCreationResponseDTO create(CommentCreationRequestDTO requestDTO) {
        User user = SecurityUtils.getUserFromPrincipal(userRepository);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_FOUND);
        }
        if (!chapterRepository.existsChapterById(requestDTO.getChapterId())) {
            throw new AppException(ErrorCode.CHAPTER_NOT_FOUND);
        }
        Comment comment = commentMapper.toComment(requestDTO);
        comment.setCreatedAt(new Date(System.currentTimeMillis()));
        comment.setState(CommentConst.HIDE.getValue());
        comment = commentRepository.save(comment);
        return commentMapper.toCommentCreationRequestDTO(comment);
    }

    @Override
    public List<CommentDTO> getComment(Integer idChapter) {
        try {
            List<Comment> comments = commentRepository.findByChapterId(idChapter);
            return comments.stream()
                    .map(commentMapper::toCommentDTOs)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            ex.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteByChapterId(Integer id) {
        List<Comment> comments = commentRepository.findByChapterId(id);
        for (Comment comment : comments) {
            comment.setIsDeleted(true);
        }
        commentRepository.saveAllAndFlush(comments);
    }

    //    ADMIN
    public DataListResponse<CommentResponseDTO> getComments(Pageable pageable) {
        DataListResponse<CommentResponseDTO> result = new DataListResponse<>();
        Page<Comment> page = commentRepository.findAll(pageable);
        List<CommentResponseDTO> data = page.getContent().stream().map(item -> commentMapper.toCommentResponseDTO(item)).toList();
        result.setCurrentPage(pageable.getPageNumber() + 1);
        result.setTotalPages(page.getTotalPages());
        result.setData(data);
        return result;
    }

    @Override
    public CommentResponseDTO getCommentDetail(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) return null;
        CommentResponseDTO commentResponseDTO = commentMapper.toCommentResponseDTO(comment);
        CommentResponseDTO.UserCommentDTO userCommentDTO = CommentResponseDTO.UserCommentDTO.builder()
                .username(comment.getUser().getUsername())
                .email(comment.getUser().getEmail())
                .avatar(comment.getUser().getAvatar())
                .build();
        commentResponseDTO.setUser(userCommentDTO);
        Optional<String> thumbnail = bookRepository.findThumbnailBookId(comment.getChapter().getBook().getId());
        commentResponseDTO.setThumbnail(thumbnail.orElse(null));
        return commentResponseDTO;
    }

    @Override
    public boolean changeState(Integer commentId, Integer state) {
        if (CommentConst.fromValue(state) == null) return false;
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null) return false;
        if (state == CommentConst.SHOW.getValue()) {
            comment.setState(CommentConst.SHOW.getValue());
        }
        if (state == 2) {
            comment.setState(CommentConst.HIDE.getValue());
        }

        commentRepository.save(comment);
        return true;
    }
}
