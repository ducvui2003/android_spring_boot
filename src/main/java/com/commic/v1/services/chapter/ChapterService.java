package com.commic.v1.services.chapter;

import com.commic.v1.dto.responses.ChapterResponse;
import com.commic.v1.entities.Chapter;
import com.commic.v1.mapper.ChapterMapper;
import com.commic.v1.repositories.IChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService implements IChapterService {
    @Autowired
    private IChapterRepository chapterRepository;
    @Autowired
    private ChapterMapper chapterMapper;

    @Override
    public List<ChapterResponse> getChaptersByBookId(Integer bookId, Sort sort) {
        List<Chapter> list = chapterRepository.findByBookId(bookId, sort);
        return chapterMapper.toChapterDTOs(list);
    }
}
