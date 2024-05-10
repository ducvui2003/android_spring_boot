package com.commic.v1.services.comment;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.CommentDTOResponse;
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
    public DataListResponse<CommentDTOResponse> get(CommentConst state, Pageable pageable) {
        DataListResponse<CommentDTOResponse> result = new DataListResponse<>();
        Page<Comment> page = commentRepository.findAllByStateOrderByCreatedAtDesc(state.getValue(), pageable);
//        List<CommentDTOResponse> data = page.getContent().stream().map(item -> commentMapper.toAdminCommentDtoResponse(item)).toList();
        result.setCurrentPage(pageable.getPageNumber());
        result.setTotalPages(page.getTotalPages());
//        result.setData(data);
        return result;
    }
}
