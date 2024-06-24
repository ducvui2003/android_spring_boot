package com.commic.v1.api.user;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.BookResponseDTO;
import com.commic.v1.dto.responses.CategoryResponse;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.services.book.IBookService;
import com.commic.v1.services.search.ISearchServices;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    @Autowired
    ISearchServices searchServices;
    @Autowired
    private IBookService bookService;

    @GetMapping()
    public ResponseEntity<List<BookResponseDTO>> getAllBook() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");


        List<BookResponseDTO> books = searchServices.getAllBook(sort);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable(value = "id", required = true) Integer id) {
        BookResponseDTO book = searchServices.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/chapter/{id}")
    public ResponseEntity<BookResponseDTO> getBookByChapterId(@PathVariable(value = "id") Integer chapterId) {
        BookResponseDTO book = bookService.getBookByChapterId(chapterId);
        return ResponseEntity.ok(book);
    }


    @GetMapping("/search")
    public APIResponse<DataListResponse<BookResponseDTO>> search(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                                 @RequestParam(name = "categoryId", required = false) String categoryId,
                                                                 @RequestParam(name = "page", defaultValue = "1") int page,
                                                                 @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Integer categoryIdNumber;
        try {
            categoryIdNumber = Integer.parseInt(categoryId);
        } catch (NumberFormatException e) {
            categoryIdNumber = null;
        }

        DataListResponse<BookResponseDTO> items = searchServices.getBook(keyword, categoryIdNumber, pageable);
        APIResponse<DataListResponse<BookResponseDTO>> apiResponse = new APIResponse<>();

        apiResponse.setCode(ErrorCode.FOUND.getCode());
        apiResponse.setMessage(ErrorCode.FOUND.getMessage());

        apiResponse.setResult(items);
        return apiResponse;
    }


    @GetMapping("/rank")
    public APIResponse<DataListResponse<BookResponseDTO>> rank(@RequestParam(name = "page", defaultValue = "1") int page,
                                                               @RequestParam(name = "size", defaultValue = "10") int size,
                                                               @RequestParam(name = "type", defaultValue = "") String type,
                                                               @RequestParam(name = "categoryId", defaultValue = "0") Integer categoryId) {

        Pageable pageable;
        pageable = PageRequest.of(page - 1, size);
        DataListResponse<BookResponseDTO> items = searchServices.getRankBy(type, pageable);
        if (categoryId == 0)
            items = searchServices.getRankBy(type, pageable);
        else items = searchServices.getRankBy(type, categoryId, pageable);
        APIResponse<DataListResponse<BookResponseDTO>> apiResponse = new APIResponse<>();
        apiResponse.setCode(ErrorCode.FOUND.getCode());
        apiResponse.setMessage(ErrorCode.FOUND.getMessage());
        apiResponse.setResult(items);
        return apiResponse;
    }

    private Sort exportSort(Map<String, String> params) {
        Sort.Order[] orders = params.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("sortField_"))
                .map(entry -> {
                    String fieldName = entry.getKey().substring("sortField_".length());
                    Sort.Direction direction = Sort.Direction.fromString(entry.getValue().toUpperCase());
                    return new Sort.Order(direction, fieldName);
                })
                .toArray(Sort.Order[]::new);

        return Sort.by(orders);
    }

    @GetMapping("/category")
    public APIResponse<List<CategoryResponse>> getCategory() {
        APIResponse<List<CategoryResponse>> apiResponse = new APIResponse<>();
        apiResponse.setCode(200);
        apiResponse.setMessage("Success");
        apiResponse.setResult(searchServices.getCategory());
        return apiResponse;
    }

    @GetMapping("/newComic")
    public APIResponse<DataListResponse<BookResponseDTO>> getNewComicOrderByPublishDate(@RequestParam(name = "page", defaultValue = "1") int page,
                                                                                        @RequestParam(name = "size", defaultValue = "10") int size,
                                                                                        @RequestParam(name = "categoryId", defaultValue = "0") Integer categoryId) {
        Pageable pageable;
        pageable = PageRequest.of(page - 1, size);
        DataListResponse<BookResponseDTO> items;
        if (categoryId == 0)
            items = searchServices.getComicByPublishDate(pageable);
        else
            items = searchServices.getComicByPublishDate(pageable, categoryId);
        APIResponse<DataListResponse<BookResponseDTO>> apiResponse = new APIResponse<>();
        apiResponse.setCode(ErrorCode.FOUND.getCode());
        apiResponse.setMessage(ErrorCode.FOUND.getMessage());
        apiResponse.setResult(items);
        return apiResponse;
    }

    @GetMapping(value = "/description/{idBook}")
    public APIResponse<BookResponseDTO> getDescription(@PathVariable("idBook") Integer id) {
        APIResponse<BookResponseDTO> apiResponse = new APIResponse<>();
        BookResponseDTO bookResponseDTO = bookService.getDescription(id);
        if (bookResponseDTO != null) {
            apiResponse.setCode(ErrorCode.FOUND.getCode());
            apiResponse.setMessage(ErrorCode.FOUND.getMessage());
        } else {
            apiResponse.setCode(ErrorCode.NOT_FOUND.getCode());
            apiResponse.setMessage(ErrorCode.NOT_FOUND.getMessage());
        }
        apiResponse.setResult(bookResponseDTO);
        return apiResponse;
    }

    @GetMapping(value = "/{id}/comment")
    public APIResponse<Integer> getAllComment(@PathVariable("id") Integer id) {
        APIResponse<Integer> apiResponse = new APIResponse<>();
     Integer totalCommentResponse = bookService.getAllComment(id);
        if (totalCommentResponse > 0) {
            apiResponse.setCode(ErrorCode.FOUND.getCode());
            apiResponse.setMessage(ErrorCode.FOUND.getMessage());
            apiResponse.setResult(totalCommentResponse);
        } else {
            apiResponse.setCode(ErrorCode.NOT_FOUND.getCode());
            apiResponse.setMessage(ErrorCode.NOT_FOUND.getMessage());
        }
        return apiResponse;
    }
}
