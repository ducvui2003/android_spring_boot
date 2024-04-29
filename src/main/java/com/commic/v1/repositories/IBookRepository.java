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
    List<Book> findAll();

    Page<Book> findAll(Pageable pageable);

    Page<Book> findByNameContaining(@Param("name") String name, Pageable pageable);

    Page<Book> findByNameContainingAndCategoriesId(@Param("name") String name, @Param("categoryId") Integer categoryId, Pageable pageable);

    @Query("SELECT book FROM Book book JOIN book.chapters chapter GROUP BY book.id ORDER BY SUM(chapter.view) DESC")
    Page<Book> findAllOrderByViewDesc(Pageable pageable);

    @Query("SELECT book FROM Book book JOIN book.chapters chapter GROUP BY book.id ORDER BY AVG(chapter.rating) DESC")
    Page<Book> findAllOrderByRatingDesc(Pageable pageable);

    @Query("SELECT DISTINCT c.name FROM Book b JOIN b.categories c WHERE b.id = :bookId")
    List<String> findCategoryNamesByBookId(Integer bookId);
}
