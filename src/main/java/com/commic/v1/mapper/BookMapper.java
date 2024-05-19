package com.commic.v1.mapper;

import com.commic.v1.dto.requests.BookRequest;
import com.commic.v1.dto.responses.BookResponseDTO;
import com.commic.v1.entities.Book;
import com.commic.v1.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {
    //    BookResponseDTO toBookResponseDTO(Book book);
    @Mapping(target = "categoryNames", source = "categories")
    BookResponseDTO toBookResponseDTO(Book book);
    Book toBook(BookRequest book);

    default List<String> mapCategoriesToCategoryNames(Set<Category> categories) {
        return categories.stream()
                .map(Category::getName)
                .collect(Collectors.toList());
    }
}
