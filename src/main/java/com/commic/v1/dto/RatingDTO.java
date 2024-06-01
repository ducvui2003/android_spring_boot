package com.commic.v1.dto;

import lombok.Data;
import java.sql.Date;

@Data
public class RatingDTO {
    private Integer id;
    private Integer chapterId;
    private Integer userId;
    private Float star;
    private Date createdAt;
    private Boolean isDeleted;
}
