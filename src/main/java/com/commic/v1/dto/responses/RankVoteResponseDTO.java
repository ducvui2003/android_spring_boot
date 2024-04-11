package com.commic.v1.dto.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class RankVoteResponseDTO extends RankResponseDTO {
    private Double vote;

    public RankVoteResponseDTO(Integer id, String name, String thumbnail, Double vote) {
        super(id, name, thumbnail);
        this.vote = vote;
    }
}
