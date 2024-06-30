package com.commic.v1.api.user;

import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.ChapterResponse;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.services.chapter.IChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chapters")
public class ChapterController {
    @Autowired
    IChapterService chapterServices;

    @GetMapping( "/book/{id}")
    public ResponseEntity<List<ChapterResponse>> getChapters(@PathVariable(value = "id") Integer bookId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        List<ChapterResponse> chapters = chapterServices.getChaptersByBookId(bookId, sort);
        return ResponseEntity.ok(chapters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChapterResponse> getChapter(@PathVariable(value = "id") Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        ChapterResponse chapter = chapterServices.getChapterById(id);
        return ResponseEntity.ok(chapter);
    }
    @GetMapping("/total-view/{id}")
    public APIResponse<Integer> getTotalView(@PathVariable(value = "id") Integer id) {
        APIResponse<Integer> apiResponse = new APIResponse<>();
        int view = chapterServices.totalViewOfEachChapter(id);
        if (view > 0) {
            apiResponse.setCode(ErrorCode.FOUND.getCode());
            apiResponse.setMessage(ErrorCode.FOUND.getMessage());
            apiResponse.setResult(view);
        } else {
            apiResponse.setCode(ErrorCode.FOUND.getCode());
            apiResponse.setMessage(ErrorCode.FOUND.getMessage());
            apiResponse.setResult(0);
        }
        return apiResponse;
    }
}
