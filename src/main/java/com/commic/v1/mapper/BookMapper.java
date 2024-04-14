package com.commic.v1.mapper;

import com.commic.v1.dto.responses.BookResponseDTO;
import com.commic.v1.entities.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookResponseDTO toBookResponseDTO(Book book);
}
