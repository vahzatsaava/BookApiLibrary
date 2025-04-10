package com.example.booklibraryapi.service.book;

import com.example.booklibraryapi.entity.Book;
import com.example.booklibraryapi.mapper.BookMapper;
import com.example.booklibraryapi.model.book.BookDto;
import com.example.booklibraryapi.model.book.BookFilter;
import com.example.booklibraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookWebService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public Page<BookDto> getBooks(BookFilter filter, Pageable pageable) {
        Specification<Book> spec = BookSpecificationBuilder.buildSpecification(filter);
        return bookRepository.findAll(spec, pageable)
                .map(bookMapper::toBookDto);
    }
}
