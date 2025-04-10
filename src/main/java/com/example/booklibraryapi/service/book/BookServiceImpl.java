package com.example.booklibraryapi.service.book;

import com.example.booklibraryapi.entity.Book;
import com.example.booklibraryapi.exceptions.BookNotFoundException;
import com.example.booklibraryapi.mapper.BookMapper;
import com.example.booklibraryapi.model.book.BookModifyDto;
import com.example.booklibraryapi.model.book.BookDto;
import com.example.booklibraryapi.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Transactional
    @Override
    public BookDto addBook(BookModifyDto create) {
        Book book = bookMapper.toBook(create);
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());

        Book bookSaved = bookRepository.save(book);
        log.info("Добавлена книга: {}", book.getTitle());

        return bookMapper.toBookDto(bookSaved);
    }

    @Transactional
    @Override
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found by vendor code  title"));
        bookRepository.delete(book);
        log.info("Удалена книга с id: {}", bookId);
    }

    @Transactional
    @Override
    public BookDto updateBook(BookModifyDto update, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Книга не найдена!"));
        updateBookFields(book, update);
        return bookMapper.toBookDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toBookDto)
                .toList();

    }

    private void updateBookFields(Book book, BookModifyDto update) {
        if (update.title() != null) book.setTitle(update.title());
        if (update.vendorCode() != null) book.setVendorCode(update.vendorCode());
        if (update.year() != null) book.setYear(update.year());
        if (update.brand() != null) book.setBrand(update.brand());
        if (update.stock() != null) book.setStock(update.stock());
        if (update.price() != null) book.setPrice(update.price());
        book.setUpdatedAt(LocalDateTime.now());
    }

}
