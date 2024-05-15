package com.commic.v1.dto.responses;

import com.commic.v1.entities.Chapter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChapterContentRespone {
    private Integer id;
    private Integer chapterId;
    private String linkImage;
}
