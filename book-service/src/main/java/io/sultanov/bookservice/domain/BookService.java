package io.sultanov.bookservice.domain;

import io.sultanov.feignclients.authorservice.AuthorBookRequest;
import io.sultanov.feignclients.authorservice.AuthorClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookMapper bookMapper;
    private final AuthorClient authorClient;

    public ResponseEntity<?> createBook(BookDto bookDto) {
        if (!authorClient.checkAuthorExisting(bookDto.getAuthor()))
            return ResponseEntity.badRequest().body("Author does not exist");
        Book book = bookMapper.insertBook(bookDto);
        authorClient.addBookToAuthor(new AuthorBookRequest(book.getAuthor(), book.getId()));
        return ResponseEntity.ok(book);
    }

    public Book getBookById(Integer id) {
        Book book = bookMapper.getBookById(id);
        if (book == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return book;
    }

    public List<Book> getBooks() {
        return bookMapper.getAllBooks();
    }
}
