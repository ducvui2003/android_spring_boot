package com.commic.v1.services.chapter;

import com.commic.v1.dto.responses.ChapterContentRespone;

import java.util.List;

public interface IChapterContentService {
    List<ChapterContentRespone> getChapterContent(Integer id);
}
