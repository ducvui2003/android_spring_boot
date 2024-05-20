package com.commic.v1.api.user;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.BookResponseDTO;
import com.commic.v1.dto.responses.CategoryResponse;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.services.book.IBookService;
import com.commic.v1.services.search.ISearchServices;
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

    @GetMapping("/search")
    public APIResponse<DataListResponse<BookResponseDTO>> search(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                                 @RequestParam(name = "categoryId", required = false) String categoryId,
                                                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                                                 @RequestParam(name = "size", defaultValue = "10") int size,
                                                                 @RequestParam Map<String, String> mapSort) {
        Pageable pageable;
        Sort sort = exportSort(mapSort);
        Integer categoryIdNumber;
        try {
            categoryIdNumber = Integer.parseInt(categoryId);
        } catch (NumberFormatException e) {
            categoryIdNumber = null;
        }
        if (sort.isEmpty())
            pageable = PageRequest.of(page, size);
        else
            pageable = PageRequest.of(page, size, sort);
        DataListResponse<BookResponseDTO> items = searchServices.getBook(keyword, categoryIdNumber, pageable);
        APIResponse<DataListResponse<BookResponseDTO>> apiResponse = new APIResponse<>();
        apiResponse.setCode(ErrorCode.BOOK_EXIST.getCode());
        apiResponse.setMessage(ErrorCode.BOOK_EXIST.getMessage());
        apiResponse.setResult(items);
        return apiResponse;
    }


    @GetMapping("/rank")
    public APIResponse<DataListResponse<BookResponseDTO>> rank(@RequestParam(name = "page", defaultValue = "0") int page,
                                                               @RequestParam(name = "size", defaultValue = "10") int size,
                                                               @RequestParam(name = "type", defaultValue = "") String type,
                                                               @RequestParam(name = "categoryId", required = false) Integer categoryId) {

        Pageable pageable;
        pageable = PageRequest.of(page, size);
        DataListResponse<BookResponseDTO> items;
        if (categoryId == null)
            items = searchServices.getRankBy(type, pageable);
        else items = searchServices.getRankBy(type, categoryId, pageable);
        APIResponse<DataListResponse<BookResponseDTO>> apiResponse = new APIResponse<>();
        apiResponse.setCode(ErrorCode.BOOK_EXIST.getCode());
        apiResponse.setMessage(ErrorCode.BOOK_EXIST.getMessage());
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
    public APIResponse<DataListResponse<BookResponseDTO>> getNewComicOrderByPublishDate(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                                        @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable;
        pageable = PageRequest.of(page, size);
        DataListResponse<BookResponseDTO> items = searchServices.getComicByPublishDate(pageable);
        APIResponse<DataListResponse<BookResponseDTO>> apiResponse = new APIResponse<>();
        apiResponse.setCode(ErrorCode.BOOK_EXIST.getCode());
        apiResponse.setMessage(ErrorCode.BOOK_EXIST.getMessage());
        apiResponse.setResult(items);
        return apiResponse;
    }

    @GetMapping(value = "/description/{idBook}")
    public APIResponse<BookResponseDTO> getDescription(@PathVariable("idBook") Integer id) {
        APIResponse<BookResponseDTO> apiResponse = new APIResponse<>();
        BookResponseDTO bookResponseDTO = bookService.getDescription(id);
        apiResponse.setCode(ErrorCode.BOOK_EXIST.getCode());
        apiResponse.setMessage(ErrorCode.BOOK_EXIST.getMessage());
        apiResponse.setResult(bookResponseDTO);
        return apiResponse;
    }
}
