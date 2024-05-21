package com.commic.v1.services.book;

import com.commic.v1.dto.requests.BookRequest;
import com.commic.v1.dto.responses.APIResponse;
import com.commic.v1.dto.responses.BookResponseDTO;

public interface IBookService {
    BookResponseDTO getDescription(Integer id);

    APIResponse<Void> addBook(BookRequest bookRequest);
    APIResponse<Void> updateBook(BookRequest bookRequest);

    APIResponse<Void> deleteBook(Integer id);
}
