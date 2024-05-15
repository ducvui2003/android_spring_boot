package com.commic.v1.api;

import com.commic.v1.dto.CommentDTO;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.ChapterContentRespone;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.services.chapter.IChapterContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ChapterContentController {
    @Autowired
    private IChapterContentService chapterContentService;

    @GetMapping(value = "/chapter-{id}")
    public APIResponse<List<ChapterContentRespone>> getChapterContent(@PathVariable Integer id) {
        APIResponse<List<ChapterContentRespone>> apiResponse = new APIResponse<>();
        List<ChapterContentRespone> chapterContentRespones = chapterContentService.getChapterContent(id);
        apiResponse.setCode(ErrorCode.FOUND.getCode());
        apiResponse.setMessage(ErrorCode.NOT_FOUND.getMessage());
        apiResponse.setResult(chapterContentRespones);
        return apiResponse;
    }
}
