package io.sultanov.bookservice.api.controller;

import io.sultanov.bookservice.domain.Book;
import io.sultanov.bookservice.domain.BookDto;
import io.sultanov.bookservice.domain.BookService;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody BookDto bookDto) {
        return bookService.createBook(bookDto);
    }

    @GetMapping
    public List<Book> getBooks() throws SchedulerException {
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    public Book getConcreteBook(@PathVariable Integer id) {
        return bookService.getBookById(id);
    }
}
