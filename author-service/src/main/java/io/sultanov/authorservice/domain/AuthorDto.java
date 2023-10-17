package io.sultanov.authorservice.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthorDto {
    private Integer id;
    private String firstName;
    private String secondName;
    private String patronymic;
    private String biography;
}
