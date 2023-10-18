package io.sultanov.feignclients.authorservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBookRequest {
    private String author;
    private Integer bookId;
}
