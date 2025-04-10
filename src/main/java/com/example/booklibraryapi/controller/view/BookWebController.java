package com.example.booklibraryapi.controller.view;

import com.example.booklibraryapi.model.book.BookDto;
import com.example.booklibraryapi.model.book.BookFilter;
import com.example.booklibraryapi.service.book.BookWebService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookWebController {
    private final BookWebService bookService;

    @GetMapping
    public String showBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Long year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        BookFilter filter = new BookFilter(title, brand, year);
        Pageable pageable = PageRequest.of(page, size);

        Page<BookDto> books = bookService.getBooks(filter, pageable);


        model.addAttribute("books", books.getContent());
        model.addAttribute("page", books);
        model.addAttribute("filter", filter);

        return "books/list";
    }
}
