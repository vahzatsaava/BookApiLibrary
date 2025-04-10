package com.example.booklibraryapi.service.book;

import com.example.booklibraryapi.entity.Book;
import com.example.booklibraryapi.model.book.BookFilter;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecificationBuilder {

    private BookSpecificationBuilder() {}

    public static Specification<Book> buildSpecification(BookFilter filter) {
        Specification<Book> spec = Specification.where(null);

        if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
            spec = spec.and(likeTitle(filter.getTitle()));
        }
        if (filter.getBrand() != null && !filter.getBrand().isEmpty()) {
            spec = spec.and(likeBrand(filter.getBrand()));
        }
        if (filter.getYear() != null) {
            spec = spec.and(equalsYear(filter.getYear()));
        }

        return spec;
    }

    private static Specification<Book> likeTitle(String title) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    private static Specification<Book> likeBrand(String brand) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%");
    }

    private static Specification<Book> equalsYear(Long year) {
        return (root, query, cb) -> cb.equal(root.get("year"), year);
    }

}
