package com.commic.v1.services.search;


import com.commic.v1.dto.responses.ItemSearchResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ISearchServices {
    public List<ItemSearchResponseDTO> searchItems(String keywordBook, Integer categoryId);
    public Page<ItemSearchResponseDTO> searchItems(String keywordBook, Integer categoryId, Pageable pageable);
}
