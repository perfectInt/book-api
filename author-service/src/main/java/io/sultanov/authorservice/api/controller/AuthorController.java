package io.sultanov.authorservice.api.controller;

import io.sultanov.authorservice.domain.AuthorDto;
import io.sultanov.authorservice.domain.AuthorService;
import io.sultanov.authorservice.domain.tables.pojos.Author;
import io.sultanov.feignclients.authorservice.AuthorBookRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public void addAuthor(@RequestBody Author author) {
        authorService.insertAuthor(author);
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable Integer id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping("/check-author")
    public Boolean checkAuthorExisting(@RequestBody String author) {
        return authorService.checkAuthor(author);
    }

    @PostMapping("/books")
    public ResponseEntity<String> addBookToAuthor(@RequestBody AuthorBookRequest request) {
        return authorService.addBookToAuthor(request);
    }
}
