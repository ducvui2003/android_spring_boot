package com.commic.v1.repositories;

import com.commic.v1.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface IBookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAll();

    Page<Book> findAll(Pageable pageable);

    Page<Book> findByNameContaining(@Param("name") String name, Pageable pageable);

    Page<Book> findByNameContainingAndCategoriesId(@Param("name") String name, @Param("categoryId") Integer categoryId, Pageable pageable);

    @Query("SELECT book FROM Book book JOIN book.chapters chapter GROUP BY book.id ORDER BY SUM(chapter.view) DESC")
    Page<Book> findAllOrderByViewDesc(Pageable pageable);

    @Query("SELECT book FROM Book book JOIN book.categories category JOIN book.chapters chapter WHERE category.id = :categoryId GROUP BY book.id ORDER BY SUM(chapter.view) DESC ")
    Page<Book> findAllOrderByViewDesc( Integer categoryId, Pageable pageable);

    @Query("SELECT book FROM Book book JOIN book.chapters chapter JOIN chapter.ratings rating GROUP BY book.id ORDER BY AVG(rating.star) DESC")
    Page<Book> findAllOrderByRatingDesc(Pageable pageable);

    @Query("SELECT book FROM Book book JOIN book.categories category JOIN book.chapters chapter JOIN chapter.ratings rating WHERE category.id= :categoryId GROUP BY book.id ORDER BY AVG(rating.star) DESC ")
    Page<Book> findAllOrderByRatingDesc(Integer categoryId, Pageable pageable);

    @Query("SELECT DISTINCT c.name FROM Book b JOIN b.categories c WHERE b.id = :bookId")
    List<String> findCategoryNamesByBookId(Integer bookId);

    @Query("SELECT DISTINCT b FROM Book b JOIN FETCH b.chapters c ORDER BY c.publishDate DESC")
    Page<Book> findByPublishDateOrderByNearestDate(Pageable pageable);

    @Query("SELECT DISTINCT b FROM Book b JOIN b.chapters c JOIN b.categories a WHERE a.id = :categoryId ORDER BY c.publishDate DESC")
    Page<Book> findByPublishDateOrderByNearestDate(Integer categoryId,Pageable pageable);

    @Query(value = "SELECT MAX(c.publish_date) OVER(PARTITION BY b.id) AS latest_chapter_date " +
            "FROM books b " +
            "JOIN chapters c ON c.book_id = b.id " +
            "WHERE b.id = :bookId " +
            "ORDER BY latest_chapter_date DESC " +
            "LIMIT 1", nativeQuery = true)
    Date findLatestChapterDateByBookId(@Param("bookId") Integer bookId);

}
