package com.commic.v1.services.book;

import com.commic.v1.dto.responses.BookResponseDTO;

public interface IBookService {
    BookResponseDTO getDescription(Integer id);
}
