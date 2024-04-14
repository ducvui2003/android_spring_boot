package com.commic.v1.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDataList<T> {
    private Integer code;
    private String message;
    private List<T> data;
    private Integer currentPage;
    private Integer totalPages;
}
