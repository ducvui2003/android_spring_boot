package com.commic.v1.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class BookResponseDTO {
    String id;
    String name;
    String author;
    String description;
    Date publishDate;
    Integer view;
    Double rating;
    String thumbnail;
    Integer quantityChapter;
    List<String> categoryNames;
    Date nearestDate;
}
