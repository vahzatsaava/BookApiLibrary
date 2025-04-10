package com.example.booklibraryapi.controller;

import com.example.booklibraryapi.model.book.BookDto;
import com.example.booklibraryapi.model.book.BookModifyDto;
import com.example.booklibraryapi.service.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Book Management", description = "API для управления книгами")
@SecurityRequirement(name = "bearerAuth")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Добавить книгу", description = "Создает новую книгу в системе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Книга успешно добавлена"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные книги")
    })
    @PostMapping
    public BookDto addBook(@Valid @RequestBody BookModifyDto createDto) {
        return bookService.addBook(createDto);
    }

    @Operation(summary = "Обновить книгу", description = "Обновляет данные книги по её vendorCode.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Книга успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Книга не найдена")
    })
    @PutMapping("/{bookId}")
    public BookDto updateBook(
            @PathVariable Long bookId,
            @Valid @RequestBody BookModifyDto updateDto) {
        return bookService.updateBook(updateDto, bookId);
    }

    @Operation(summary = "Удалить книгу", description = "Удаляет книгу по её vendorCode.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Книга успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Книга не найдена")
    })
    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
    }

    @Operation(summary = "Получить книги все", description = "Получает все книги доступные.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Книги найдены"),
            @ApiResponse(responseCode = "404", description = "Книг нету")
    })
    @GetMapping("/{bookId}")
    public List<BookDto> getAll() {
        return bookService.getAll();
    }

}
