package com.commic.v1.api;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.BookResponseDTO;
import com.commic.v1.dto.responses.CategoryResponseDTO;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.services.search.ISearchServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/book")
public class BookController {
    @Autowired
    ISearchServices searchServices;

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
    public APIResponse<List<CategoryResponseDTO>> getCategory() {
        APIResponse<List<CategoryResponseDTO>> apiResponse = new APIResponse<>();
        apiResponse.setCode(200);
        apiResponse.setMessage("Success");
        apiResponse.setResult(searchServices.getCategory());
        return apiResponse;
    }

    @GetMapping("/latestdate")
    public APIResponse<DataListResponse<BookResponseDTO>> getLatestDate(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                        @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable;
        pageable = PageRequest.of(page, size);
        DataListResponse<BookResponseDTO> items = searchServices.getBookLatest(pageable);
        APIResponse<DataListResponse<BookResponseDTO>> apiResponse = new APIResponse<>();
        apiResponse.setCode(ErrorCode.BOOK_EXIST.getCode());
        apiResponse.setMessage(ErrorCode.BOOK_EXIST.getMessage());
        apiResponse.setResult(items);
        return apiResponse;
    }
}
