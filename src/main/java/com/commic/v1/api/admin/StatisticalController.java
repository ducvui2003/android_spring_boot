package com.commic.v1.api.admin;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.StatisticalBookResponse;
import com.commic.v1.dto.responses.StatisticalResponse;
import com.commic.v1.services.book.IBookService;
import com.commic.v1.services.chapter.ChapterService;
import com.commic.v1.services.comment.CommentServiceImp;
import com.commic.v1.services.rating.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController(value = "StatisticalControllerAdmin")
@RequestMapping("/api/v1/admin/statistical")
//@PreAuthorize("hasAuthority('ADMIN')")
public class StatisticalController {
    @Autowired
    IBookService bookService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentServiceImp commentServiceImp;

    @GetMapping()
    public APIResponse<StatisticalResponse> statisticalResult() {
        StatisticalResponse statisticalResponse = new StatisticalResponse();
        APIResponse<StatisticalResponse> apiResponse = new APIResponse<>();

        Integer countBooks = bookService.countAllBooks();
        Integer countChapter = chapterService.countAllChapter();
        Integer countViews = bookService.countAllViews();
        Integer countRating = ratingService.countAllRating();
        Integer countComment = commentServiceImp.countAllComment();

        statisticalResponse.setCountBooks(countBooks);
        statisticalResponse.setCountChapters(countChapter);
        statisticalResponse.setCountViews(countViews);
        statisticalResponse.setCountRating(countRating);
        statisticalResponse.setCountComment(countComment);

        apiResponse.setResult(statisticalResponse);

        return apiResponse;
    }

    @GetMapping("/book/{id}")
    public APIResponse<StatisticalBookResponse> statisticalBookResult(@PathVariable(name = "id") Integer id) {
        StatisticalBookResponse statisticalBookResponse = new StatisticalBookResponse();
        APIResponse<StatisticalBookResponse> apiResponse = new APIResponse<>();


        statisticalBookResponse.setIdBook(id);
        statisticalBookResponse.setThumbnail(bookService.getBookThumbnailByBookId(id));
        statisticalBookResponse.setCountBookChapters(bookService.countAllChapterByBookId(id));

        Float avgRating = bookService.avaRatingByBookId(id);
        statisticalBookResponse.setAverageBookRating(avgRating);

        statisticalBookResponse.setCountBookRating(ratingService.countAllRatingByBookId(id));
        statisticalBookResponse.setCountBookViews(bookService.countAllViewsByBookId(id));
        statisticalBookResponse.setCountBookComment(bookService.countAllCommentByBookId(id));

        apiResponse.setResult(statisticalBookResponse);
        return apiResponse;
    }
}
