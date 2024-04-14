package com.commic.v1.services.search;

import com.commic.v1.dto.responses.ItemSearchResponseDTO;
import com.commic.v1.entities.Book;
import com.commic.v1.repositories.IBookRepository;
import com.commic.v1.repositories.IChapterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServicesImp implements ISearchServices {
    @Autowired
    private IBookRepository bookRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    IChapterRepository chapterRepository;

    @Override
    public List<ItemSearchResponseDTO> searchItems(String keywordBook, Integer categoryId) {
        List<Book> books;
        if (categoryId == null)
            books = bookRepository.findByNameContaining(keywordBook);
        else
            books = bookRepository.findByNameAndCategoryId(keywordBook, categoryId);
        List<ItemSearchResponseDTO> result = new ArrayList<>();
        for (Book book : books) {
            ItemSearchResponseDTO itemSearchResponseDTO = modelMapper.map(book, ItemSearchResponseDTO.class);
            Double starAvg = chapterRepository.countStarAvgByBookId(book.getId());
            itemSearchResponseDTO.setRating(starAvg);
            Long views = chapterRepository.countByBookId(book.getId());
            itemSearchResponseDTO.setView(views.intValue());
            result.add(itemSearchResponseDTO);
        }
        return result;
    }

    @Override
    public Page<ItemSearchResponseDTO> searchItems(String keywordBook, Integer categoryId, Pageable pageable) {
        Page<Book> booksPage;
        if (categoryId == null)
            booksPage = bookRepository.findByNameContaining(keywordBook, pageable);
        else
            booksPage = bookRepository.findByNameAndCategoryId(keywordBook, categoryId, pageable);
        return booksPage.map(book -> {
            ItemSearchResponseDTO itemSearchResponseDTO = modelMapper.map(book, ItemSearchResponseDTO.class);
            Double starAvg = chapterRepository.countStarAvgByBookId(book.getId());
            itemSearchResponseDTO.setRating(starAvg);
            Long views = chapterRepository.countByBookId(book.getId());
            itemSearchResponseDTO.setView(views.intValue());
            return itemSearchResponseDTO;
        });
    }
}
