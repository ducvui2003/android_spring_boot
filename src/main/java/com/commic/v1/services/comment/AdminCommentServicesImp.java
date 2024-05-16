package com.commic.v1.services.comment;

import com.commic.v1.dto.CommentDTO;
import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.CommentResponseDTO;
import com.commic.v1.entities.Comment;
import com.commic.v1.mapper.CommentMapper;
import com.commic.v1.repositories.ICommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCommentServicesImp implements IAdminCommentServices {
    private static final Logger log = LoggerFactory.getLogger(AdminCommentServicesImp.class);
    @Autowired
    ICommentRepository commentRepository;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public DataListResponse<CommentResponseDTO> get(Pageable pageable) {
        DataListResponse<CommentResponseDTO> result = new DataListResponse<>();
        Page<Comment> page = commentRepository.findAll(pageable);
        List<CommentResponseDTO> data = page.getContent().stream().map(item -> commentMapper.toCommentResponseDTO(item)).toList();
        result.setCurrentPage(pageable.getPageNumber());
        result.setTotalPages(page.getTotalPages());
        result.setData(data);
        return result;
    }
}
