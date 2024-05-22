package com.commic.v1.services.chapter;

import com.commic.v1.dto.responses.ChapterContentRespone;
import com.commic.v1.dto.responses.ChapterResponse;
import com.commic.v1.entities.Chapter;
import com.commic.v1.entities.Rating;
import com.commic.v1.mapper.ChapterMapper;
import com.commic.v1.repositories.IChapterRepository;
import com.commic.v1.services.comment.ICommentServices;
import com.commic.v1.services.history.IHistoryService;
import com.commic.v1.services.rating.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterService implements IChapterService {
    @Autowired
    private IChapterRepository chapterRepository;
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private ICommentServices commentServices;
    @Autowired
    private IHistoryService historyService;
    @Autowired
    private IRatingService ratingService;
    @Autowired
    private IChapterContentService contentService;

    @Override
    public List<ChapterResponse> getChaptersByBookId(Integer bookId, Sort sort) {
        List<Chapter> list = chapterRepository.findByBookId(bookId, sort);
        return chapterMapper.toChapterDTOs(list);
    }

    @Override
    public void deleteByBookId(Integer id) {
        List<Chapter> chapters = chapterRepository.findByBookId(id, Sort.unsorted());
        for (Chapter chapter : chapters) {
            chapter.setIsDeleted(true);

            commentServices.deleteByChapterId(chapter.getId());

            //rating
            ratingService.deleteByChapterId(chapter.getId());

            contentService.deleteByChapterId(chapter.getId());

            //chapter content
            historyService.deleteByChapterId(chapter.getId());
        }

        chapterRepository.saveAllAndFlush(chapters);
    }

    @Override
    public ChapterResponse getChapterById(Integer id) {
        Example<Chapter> example = Example.of(Chapter.builder().id(id).isDeleted(false).build());
        Chapter chapter = chapterRepository.findOne(example).orElse(null);
        if (chapter != null) {
            List<Rating> ratings = ratingService.findByChapterId(chapter.getId());
            float rating = (float) ratings.stream().mapToDouble(Rating::getStar).average().orElse(0);

            List<ChapterContentRespone> content = contentService.getChapterContent(chapter.getId());

            ChapterResponse response = chapterMapper.toChapterDTO(chapter);
            response.setChapterContent(content);
            response.setRating(rating);
            return response;
        }

        return null;
    }
}
