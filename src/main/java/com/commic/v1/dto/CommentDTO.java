package com.commic.v1.dto;

import com.commic.v1.entities.Chapter;
import com.commic.v1.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.Date;
@Data
public class CommentDTO {
    private Integer id;
    private User user;
    private Chapter chapter;
    private String content;
    private Date createdAt;
    private Integer state;
}
