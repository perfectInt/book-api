package io.sultanov.authorservice.domain;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorBookRepository {

    @Autowired
    private DSLContext dsl;

    public ResponseEntity<String> addBookToAuthor(Integer authorId, Integer bookId) {
        dsl.insertInto(Tables.AUTHOR_BOOK)
                .set(Tables.AUTHOR_BOOK.AUTHOR_ID, authorId)
                .set(Tables.AUTHOR_BOOK.BOOK_ID, bookId)
                .execute();

        return ResponseEntity.ok("Relationships have been updated!");
    }
}
