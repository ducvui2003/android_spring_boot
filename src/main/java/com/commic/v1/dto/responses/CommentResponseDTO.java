package com.commic.v1.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDTO {
    private Integer id;
    private Integer userId;
    private String bookName;
    private String chapterNumber;
    private String content;
    private Date createdAt;
    private Integer state;
}
