package com.commic.v1.repositories;

import com.commic.v1.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, Long> {
    //    @Query("SELECT book.id as id, book.name as name, SUM (chapter.view) as view " +
//            "FROM Book book JOIN book.chapters chapter " +
//            "group by book.id")
//    List<RankViewResponseDTO> getRankingView(String order);
    List<Book> findAll();

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE b.name = :name AND (c.id = :categoryId OR :categoryId IS NULL)")
    List<Book> findByNameAndCategoryId(@Param("name") String name, @Param("categoryId") Integer categoryId);

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE b.name = :name AND (c.id = :categoryId OR :categoryId IS NULL)")
    Page<Book> findByNameAndCategoryId(@Param("name") String name, @Param("categoryId") Integer categoryId, Pageable pageable);

    List<Book> findByNameContaining(String keyword);

    Page<Book> findByNameContaining(String keyword, Pageable pageable);
}
