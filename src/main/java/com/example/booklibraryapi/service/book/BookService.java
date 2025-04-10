package com.example.booklibraryapi.service.book;

import com.example.booklibraryapi.model.book.BookModifyDto;
import com.example.booklibraryapi.model.book.BookDto;

import java.util.List;

public interface BookService {
    BookDto addBook(BookModifyDto create);
    void deleteBook(Long bookId);
    BookDto updateBook(BookModifyDto update, Long bookId);

    List<BookDto> getAll();
}
