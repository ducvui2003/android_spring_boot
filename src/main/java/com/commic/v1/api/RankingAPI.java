package com.commic.v1.api;

import com.commic.v1.dto.responses.RankResponseDTO;
import com.commic.v1.services.ranking.IRankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/manga")
public class RankingAPI {
    @Autowired
    IRankService rankService;

    @GetMapping("/greating")
    public String getRanking() {
       return "hello";
    }

    @GetMapping("/ranking")
    public List<RankResponseDTO> getVotes(@RequestParam(value = "sort", required = true) String sort, @RequestParam(value = "order", defaultValue = "ASC") String order) {
        List<RankResponseDTO> votes = rankService.getRanking(sort, order);
        return votes;
    }
}
