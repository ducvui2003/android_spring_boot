package com.commic.v1.dto.responses;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemSearchResponseDTO {
    private String id;
    private String name;
    private String category;
    private Integer view;
    private Double rating;
    private String thumbnail;
    private Integer quantityChapter;
}
