package com.commic.v1.dto.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankVoteResponseDTO extends RankResponseDTO {
    private Double vote;

    public RankVoteResponseDTO(Integer id, String name) {
        super(id, name);
    }

    public RankVoteResponseDTO(Integer id, String name, Double vote) {
        super(id, name);
        this.vote = vote;
    }
}
