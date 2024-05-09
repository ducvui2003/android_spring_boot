package com.commic.v1.services.search;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.BookResponseDTO;
import com.commic.v1.dto.responses.CategoryResponseDTO;
import com.commic.v1.entities.Category;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISearchServices {
    DataListResponse<BookResponseDTO> getBook(Pageable pageable);

    DataListResponse<BookResponseDTO> getBook(String containName, Pageable pageable);

    DataListResponse<BookResponseDTO> getBook(String containName, Integer categoryId, Pageable pageable);

    DataListResponse<BookResponseDTO> getRankBy(String type, Pageable pageable);

    List<CategoryResponseDTO> getCategory();

    DataListResponse<BookResponseDTO> getBookLatest(Pageable pageable);
}
