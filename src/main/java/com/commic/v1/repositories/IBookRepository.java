package com.commic.v1.repositories;

import com.commic.v1.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {
//    @Query("SELECT book.id as id, book.name as name, SUM (chapter.view) as view " +
//            "FROM Book book JOIN book.chapters chapter " +
//            "group by book.id")
//    List<RankViewResponseDTO> getRankingView(String order);
    List<Book> findAll();
}
