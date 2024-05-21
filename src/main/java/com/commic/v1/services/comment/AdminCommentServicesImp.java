package com.commic.v1.services.comment;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.CommentResponseDTO;
import com.commic.v1.entities.Comment;
import com.commic.v1.mapper.CommentMapper;
import com.commic.v1.repositories.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCommentServicesImp implements IAdminCommentServices {
    @Autowired
    ICommentRepository commentRepository;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public DataListResponse<CommentResponseDTO> getComments(Pageable pageable) {
        DataListResponse<CommentResponseDTO> result = new DataListResponse<>();
        Page<Comment> page = commentRepository.findAll(pageable);
        System.out.println(page.getContent());
        List<CommentResponseDTO> data = page.getContent().stream().map(item -> commentMapper.toCommentResponseDTO(item)).toList();
        result.setCurrentPage(pageable.getPageNumber());
        result.setTotalPages(page.getTotalPages());
        result.setData(data);
        return result;
    }
}
