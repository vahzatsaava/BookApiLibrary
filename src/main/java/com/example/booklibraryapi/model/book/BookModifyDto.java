package com.example.booklibraryapi.model.book;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;


public record BookModifyDto(@NotBlank(message = "Vendor code cannot be blank")
                            @Schema(description = "Уникальный код книги", example = "ABC123")
                            String vendorCode,

                            @NotBlank(message = "Title cannot be blank")
                            @Schema(description = "Название книги", example = "Мастер и Маргарита")
                            String title,

                            @NotNull(message = "Year cannot be null")
                            @Min(value = 1000, message = "Year must be greater than or equal to 1000")
                            @Max(value = 9999, message = "Year must be less than or equal to 9999")
                            @Schema(description = "Год выпуска книги", example = "1967")
                            Long year,

                            @NotBlank(message = "Brand cannot be blank")
                            @Schema(description = "Издатель", example = "Эксмо")
                            String brand,

                            @NotNull(message = "Stock cannot be null")
                            @Min(value = 0, message = "Stock must be greater than or equal to 0")
                            @Schema(description = "Количество книг в наличии", example = "500")
                            Integer stock,

                            @NotNull(message = "Price cannot be null")
                            @DecimalMin(value = "0.01", message = "Price must be greater than 0")
                            @Schema(description = "Цена книги", example = "499.99")
                            BigDecimal price) {
}
