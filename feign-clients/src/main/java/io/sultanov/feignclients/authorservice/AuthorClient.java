package io.sultanov.feignclients.authorservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "author",
        url = "${clients.author.url}"
)
public interface AuthorClient {

    @PostMapping("/api/v1/authors/check-author")
    Boolean checkAuthorExisting(@RequestBody String author);

    @PostMapping("/api/v1/authors/books")
    ResponseEntity<String> addBookToAuthor(@RequestBody AuthorBookRequest request);
}
