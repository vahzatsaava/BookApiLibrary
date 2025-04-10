package com.example.booklibraryapi.mapper;

import com.example.booklibraryapi.entity.Book;
import com.example.booklibraryapi.model.book.BookDto;
import com.example.booklibraryapi.model.book.BookModifyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {
    BookDto toBookDto(Book book);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Book toBook(BookModifyDto bookInitializeDto);
}
