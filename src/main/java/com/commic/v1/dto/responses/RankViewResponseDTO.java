package com.commic.v1.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankViewResponseDTO extends RankResponseDTO {
    private Integer view;

    public RankViewResponseDTO(Integer id, String name) {
        super(id, name);
    }

    public RankViewResponseDTO(Integer id, String name, Integer view) {
        super(id, name);
        this.view = view;
    }

}
