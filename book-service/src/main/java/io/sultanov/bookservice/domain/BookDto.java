package io.sultanov.bookservice.domain;

import lombok.Data;

@Data
public class BookDto {
    private String author;
    private String title;
    private String description;
}
