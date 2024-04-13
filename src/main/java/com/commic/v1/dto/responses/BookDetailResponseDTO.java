package com.commic.v1.dto.responses;


import com.commic.v1.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class BookDetailResponseDTO {
    String id;
    String name;
    String author;
    String description;
    Set<Category> categories;
    Integer view;
    Double rating;
    String thumbnail;
    Integer quantityChapter;
}
