package com.commic.v1.dto.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class ChapterResponse {
    private Integer id;
    private String name;
    private Date publishDate;
    private Integer view;
}
