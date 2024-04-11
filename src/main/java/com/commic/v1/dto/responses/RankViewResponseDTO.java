package com.commic.v1.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RankViewResponseDTO extends RankResponseDTO {
    private Integer view;

    public RankViewResponseDTO(Integer id, String name, String thumbnail, Integer view) {
        super(id, name, thumbnail);
        this.view = view;
    }
}
