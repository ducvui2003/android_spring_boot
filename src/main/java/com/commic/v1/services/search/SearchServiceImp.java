package com.commic.v1.services.search;

import com.commic.v1.dto.DataListResponse;
import com.commic.v1.dto.responses.BookResponseDTO;
import com.commic.v1.dto.responses.CategoryResponseDTO;
import com.commic.v1.entities.Book;
import com.commic.v1.entities.Category;
import com.commic.v1.exception.AppException;
import com.commic.v1.exception.ErrorCode;
import com.commic.v1.mapper.BookMapper;
import com.commic.v1.repositories.IBookRepository;
import com.commic.v1.repositories.ICategoryRepository;
import com.commic.v1.repositories.IChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SearchServiceImp implements ISearchServices {
    @Autowired
    private IBookRepository bookRepository;
    @Autowired
    private IChapterRepository chapterRepository;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public DataListResponse<BookResponseDTO> getBook(Pageable pageable) {
        DataListResponse<BookResponseDTO> result = new DataListResponse<>();
        Page<Book> page = bookRepository.findAll(pageable);
        List<Book> books = page.getContent();
        if (page.isEmpty()) throw new AppException(ErrorCode.BOOK_EMPTY);
        List<BookResponseDTO> data = bookToResponseDTO(books);
        result.setCurrentPage(pageable.getPageNumber() + 1);
        result.setTotalPages(page.getTotalPages());
        result.setData(data);
        return getBook(null, pageable);
    }

    @Override
    public DataListResponse<BookResponseDTO> getBook(String containName, Pageable pageable) {
        DataListResponse<BookResponseDTO> result = new DataListResponse<>();
        Page<Book> page = bookRepository.findByNameContaining(containName, pageable);
        if (page.isEmpty()) throw new AppException(ErrorCode.BOOK_EMPTY);
        List<Book> books = page.getContent();
        List<BookResponseDTO> data = bookToResponseDTO(books);
        result.setCurrentPage(pageable.getPageNumber() + 1);
        result.setTotalPages(page.getTotalPages());
        result.setData(data);
        return result;
    }


    @Override
    public DataListResponse<BookResponseDTO> getBook(String containName, Integer categoryId, Pageable pageable) {
        DataListResponse<BookResponseDTO> result = new DataListResponse<>();
        Page<Book> page;
        if (categoryId == null)
            page = bookRepository.findByNameContaining(containName, pageable);
        else
            page = bookRepository.findByNameContainingAndCategoriesId(containName, categoryId, pageable);
        if (page.isEmpty()) throw new AppException(ErrorCode.BOOK_EMPTY);
        List<Book> books = page.getContent();
        List<BookResponseDTO> data = bookToResponseDTO(books);
        result.setCurrentPage(pageable.getPageNumber() + 1);
        result.setTotalPages(page.getTotalPages());
        result.setData(data);
        return result;
    }

    private List<BookResponseDTO> bookToResponseDTO(List<Book> books) {
        List<BookResponseDTO> result = new ArrayList<>();
        for (Book book : books) {
            Double starAvg = Optional.ofNullable(chapterRepository.countStarAvgByBookId(book.getId())).orElse(0.0);
            Integer views = Optional.ofNullable(chapterRepository.countViewByBookId(book.getId())).orElse(0);
            Integer quantityChapter = Optional.ofNullable(chapterRepository.countByBookId(book.getId())).orElse(0);
            BookResponseDTO bookResponseDTO = bookMapper.toBookResponseDTO(book);
            bookResponseDTO.setRating(starAvg);
            bookResponseDTO.setView(views);
            bookResponseDTO.setQuantityChapter(quantityChapter);
            bookResponseDTO.setPublishDate(chapterRepository.findFirstPublishDateByBookId(book.getId()));
            List<String> categories = bookRepository.findCategoryNamesByBookId(book.getId());
            bookResponseDTO.setCategoryNames(categories);
            result.add(bookResponseDTO);
        }
        return result;
    }

    @Override
    public DataListResponse<BookResponseDTO> getRankBy(String type, Pageable pageable) {
        DataListResponse<BookResponseDTO> result = new DataListResponse<>();
        Page<Book> page;
        switch (type.toUpperCase()) {
            case "RATING" -> page = bookRepository.findAllOrderByRatingDesc(pageable);

            case "VIEW" -> page = bookRepository.findAllOrderByViewDesc(pageable);

            default -> throw new AppException(ErrorCode.PARAMETER_NOT_VALID);
        }
        if (page.isEmpty()) throw new AppException(ErrorCode.BOOK_EMPTY);
        List<Book> books = page.getContent();
        List<BookResponseDTO> data = bookToResponseDTO(books);
        result.setCurrentPage(pageable.getPageNumber() + 1);
        result.setTotalPages(page.getTotalPages());
        result.setData(data);
        return result;
    }

    @Override
    public List<CategoryResponseDTO> getCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponseDTO> categoryResponseDTOS = new ArrayList<>();
        if (categories.isEmpty()) throw new AppException(ErrorCode.CATEGORY_EMPTY);
        for (Category category : categories) {
            CategoryResponseDTO categoryResponseDTO = new CategoryResponseDTO();
            categoryResponseDTO.setId(category.getId());
            categoryResponseDTO.setName(category.getName());
            categoryResponseDTOS.add(categoryResponseDTO);
        }
        return categoryResponseDTOS;
    }

    @Override
    public DataListResponse<BookResponseDTO> getRankBy(String type, Integer categoryId, Pageable pageable) {
        DataListResponse<BookResponseDTO> result = new DataListResponse<>();
        Page<Book> page;
        switch (type.toUpperCase()) {
            case "RATING" -> page = bookRepository.findAllOrderByRatingDesc(categoryId, pageable);

            case "VIEW" -> page = bookRepository.findAllOrderByViewDesc(categoryId, pageable);

            default -> throw new AppException(ErrorCode.PARAMETER_NOT_VALID);
        }
        if (page.isEmpty()) throw new AppException(ErrorCode.BOOK_EMPTY);
        List<Book> books = page.getContent();
        List<BookResponseDTO> data = bookToResponseDTO(books);
        result.setCurrentPage(pageable.getPageNumber() + 1);
        result.setTotalPages(page.getTotalPages());
        result.setData(data);
        return result;
    }

    @Override
    public DataListResponse<BookResponseDTO> getBookLatest(Pageable pageable) {
        Page<Book> page = bookRepository.findBooksOrderByLatestChapterPublishDate(pageable);
        DataListResponse<BookResponseDTO> result = new DataListResponse<>();
        List<Book> books = page.getContent();
        List<BookResponseDTO> data = bookToResponseDTO(books);
        result.setCurrentPage(pageable.getPageNumber() + 1);
        result.setTotalPages(page.getTotalPages());
        result.setData(data);
        return result;
    }
}
