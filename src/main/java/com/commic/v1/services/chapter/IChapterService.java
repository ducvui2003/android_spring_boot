package com.commic.v1.services.chapter;

import com.commic.v1.dto.responses.ChapterResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IChapterService {
    List<ChapterResponse> getChaptersByBookId(Integer bookId, Sort sort);

    void deleteByBookId(Integer id);

    ChapterResponse getChapterById(Integer id);

    Integer countAllChapter();
    Integer totalViewOfEachChapter(Integer id);
}
