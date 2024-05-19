package com.commic.v1.services.search;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.BookResponseDTO;
import com.commic.v1.dto.responses.CategoryResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface ISearchServices {
    DataListResponse<BookResponseDTO> getBook(Pageable pageable);

    DataListResponse<BookResponseDTO> getBook(String containName, Pageable pageable);

    DataListResponse<BookResponseDTO> getBook(String containName, Integer categoryId, Pageable pageable);

    DataListResponse<BookResponseDTO> getRankBy(String type, Pageable pageable);

    DataListResponse<BookResponseDTO> getRankBy(String type, Integer categoryId, Pageable pageable);

    List<CategoryResponse> getCategory();

    DataListResponse<BookResponseDTO> getComicByPublishDate(Pageable pageable);

    List<BookResponseDTO> getAllBook(Sort pageable);

    BookResponseDTO getBookById(Integer id);
}
