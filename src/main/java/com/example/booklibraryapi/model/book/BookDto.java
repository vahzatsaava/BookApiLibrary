package com.example.booklibraryapi.model.book;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private String id;

    private String vendorCode;

    private String title;

    private Long year;

    private String brand;

    private Integer stock;

    private BigDecimal price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
