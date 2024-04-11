package com.commic.v1.api;

import com.commic.v1.dto.ResponseDataList;
import com.commic.v1.dto.responses.ItemSearchResponseDTO;
import com.commic.v1.services.search.ISearchServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/book")
public class BookAPI {
    @Autowired
    ISearchServices searchServices;

    @GetMapping("/search")
    public ResponseDataList search(@RequestParam(name = "keyword") String keyword,
                                   @RequestParam(name = "categoryId", required = false) Integer categoryId,
                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                   @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ItemSearchResponseDTO> items = searchServices.searchItems(keyword, categoryId, pageable);
        if (items.isEmpty()) {
            ResponseDataList<ItemSearchResponseDTO> responseData = new ResponseDataList<>();
            responseData.setCode(HttpStatus.NOT_FOUND.value());
            responseData.setMessage(HttpStatus.NOT_FOUND.getReasonPhrase());
            return responseData;
        }
        ResponseDataList<ItemSearchResponseDTO> responseData = new ResponseDataList<>();
        responseData.setCode(HttpStatus.OK.value());
        responseData.setMessage(HttpStatus.OK.getReasonPhrase());
        responseData.setData(items.getContent());
        responseData.setCurrentPage(pageable.getPageNumber() + 1);
        responseData.setTotalPages(pageable.getPageSize());
        return responseData;
    }
}
