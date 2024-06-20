package com.commic.v1.dto.responses;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatisticalResponse {
    private int countBooks;
    private int countChapters;
    private int countViews;
    private int countRating;
    private int countComment;
}
