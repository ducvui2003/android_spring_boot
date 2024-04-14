package com.commic.v1.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class RankResponseDTO {
    private Integer id;
    private String name;
}
