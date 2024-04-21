package com.commic.v1.services.search;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.BookResponseDTO;
import org.springframework.data.domain.Pageable;

public interface ISearchServices {
    DataListResponse<BookResponseDTO> getBook(Pageable pageable);

    DataListResponse<BookResponseDTO> getBook(String containName, Pageable pageable);

    DataListResponse<BookResponseDTO> getBook(String containName, Integer categoryId, Pageable pageable);

    DataListResponse<BookResponseDTO> getRankBy(String type, Pageable pageable);
}
