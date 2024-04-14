package com.commic.v1.services.ranking;

import com.commic.v1.dto.responses.RankResponseDTO;

import java.util.List;

public interface IRankService {
    List<RankResponseDTO> getRanking(String type, String order);

}
