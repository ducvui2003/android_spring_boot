package com.commic.v1.mapper;

import com.commic.v1.dto.responses.ChapterContentRespone;
import com.commic.v1.entities.Chapter;
import com.commic.v1.entities.ChapterContent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChapterMapper {
    @Mapping(source = "chapter.id", target = "chapterId")
    ChapterContentRespone convertToDTO(ChapterContent chapter);
}
