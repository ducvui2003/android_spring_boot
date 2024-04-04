package com.commic.v1.services.ranking;

import com.commic.v1.dto.responses.RankResponseDTO;
import com.commic.v1.dto.responses.RankViewResponseDTO;
import com.commic.v1.dto.responses.RankVoteResponseDTO;
import com.commic.v1.entities.Book;
import com.commic.v1.entities.Chapter;
import com.commic.v1.repositories.IBookRepository;
import com.commic.v1.repositories.IChapterRepository;
import com.commic.v1.repositories.IRankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RankImplement implements IRankService {
    @Autowired
    private IRankingRepository rankingRepository;
    @Autowired
    private IBookRepository bookRepository;
    @Autowired
    private IChapterRepository chapterRepository;

    @Override
    public List<RankResponseDTO> getRanking(String type, String order) {
        List<RankResponseDTO> result = new ArrayList<>();
        switch (type) {
            case "vote" -> {
                List<Book> books = bookRepository.findAll();
                List<RankVoteResponseDTO> rankVote = new ArrayList<>();
                for (Book book : books) {
                    Integer id = book.getId();
                    String name = book.getName();
                    Double starAvg = chapterRepository.countStarAvgByBookId(book.getId());
                    RankVoteResponseDTO rankResponseDTO = new RankVoteResponseDTO(id, name, starAvg);
                    rankVote.add(rankResponseDTO);
                }
                result.addAll(rankVote);
            }
            case "view" -> {
                List<Book> books = bookRepository.findAll();
                List<RankViewResponseDTO> rankView = new ArrayList<>();
                for (Book book : books) {
                    Integer id = book.getId();
                    String name = book.getName();
                    List<Chapter> chapters = chapterRepository.findAllByBookId(book.getId());
                    Integer star = chapters.stream().mapToInt(chapter -> chapter.getView()).sum();
                    RankViewResponseDTO rankViewResponseDTO = new RankViewResponseDTO(id, name, star);
                    rankView.add(rankViewResponseDTO);
                }
                result.addAll(rankView);
            }
        }
        return result;
    }
}
