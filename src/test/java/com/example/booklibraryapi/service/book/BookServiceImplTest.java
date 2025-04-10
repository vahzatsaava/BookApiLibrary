package com.example.booklibraryapi.service.book;

import com.example.booklibraryapi.entity.Book;
import com.example.booklibraryapi.exceptions.BookNotFoundException;
import com.example.booklibraryapi.mapper.BookMapper;
import com.example.booklibraryapi.model.book.BookDto;
import com.example.booklibraryapi.model.book.BookModifyDto;
import com.example.booklibraryapi.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private BookDto bookDto;
    private BookModifyDto modifyDto;

    @BeforeEach
    void setUp() {
        book = new Book(1L, "V123", "Book Title", 2020L, "Brand", 10, new BigDecimal("99.99"),
                LocalDateTime.now(), LocalDateTime.now());
        bookDto = new BookDto("1", "V123", "Book Title", 2020L, "Brand", 10, new BigDecimal("99.99"),
                LocalDateTime.now(), LocalDateTime.now());
        modifyDto = new BookModifyDto("V123", "Book Title", 2020L, "Brand", 10, new BigDecimal("99.99"));
    }

    @Test
    void addBook_shouldSaveBookAndReturnDto() {
        when(bookMapper.toBook(modifyDto)).thenReturn(book);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toBookDto(book)).thenReturn(bookDto);

        BookDto result = bookService.addBook(modifyDto);

        assertEquals(bookDto, result);
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void deleteBook_shouldDeleteBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.deleteBook(1L);

        verify(bookRepository).delete(book);
    }

    @Test
    void deleteBook_shouldThrowExceptionIfNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
    }

    @Test
    void updateBook_shouldUpdateAndReturnDto() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toBookDto(book)).thenReturn(bookDto);

        BookDto result = bookService.updateBook(modifyDto, 1L);

        assertEquals(bookDto, result);
        verify(bookRepository).save(book);
    }

    @Test
    void updateBook_shouldThrowExceptionIfNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.updateBook(modifyDto, 1L));
    }

    @Test
    void getAll_shouldReturnListOfBooks() {
        List<Book> books = List.of(book);
        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.toBookDto(book)).thenReturn(bookDto);

        List<BookDto> result = bookService.getAll();

        assertEquals(1, result.size());
        assertEquals(bookDto, result.get(0));
    }
}