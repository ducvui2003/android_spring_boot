package com.commic.v1.dto.responses;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class CommentDTOResponse {
    private Integer id;
    private Integer chapterId;
    private Integer userId;
    private String content;
    private String state;
    private Date createAt;

}
