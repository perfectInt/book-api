package io.sultanov.bookservice.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Book {
    private Integer id;
    private String author;
    private String title;
    private String description;
}
