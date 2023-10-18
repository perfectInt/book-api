package io.sultanov.authorservice.domain;

import io.sultanov.authorservice.domain.tables.pojos.Author;
import io.sultanov.feignclients.authorservice.AuthorBookRequest;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record5;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AuthorService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private final AuthorRepository authorRepository;
    private final AuthorBookRepository authorBookRepository;

    public void insertAuthor(Author author) {
        authorRepository.insertAuthor(author);

        sendMessageToAuthorNotificationTopic(author.toString() + "\n");
    }

    public AuthorDto getAuthorById(Integer id) {
        Record record = authorRepository.getAuthorById(id);

        if (record == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return AuthorDto.builder()
                .id(record.get("id", Integer.class))
                .firstName(record.get("first_name", String.class))
                .secondName(record.get("second_name", String.class))
                .patronymic(record.get("patronymic", String.class))
                .biography(record.get("biography", String.class))
                .build();
    }

    private void sendMessageToAuthorNotificationTopic(String message) {
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send("author-notification-topic", message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });
    }

    public Boolean checkAuthor(String author) {
        author = author.split(" ")[0];
        return authorRepository.existsBySurname(author);
    }

    public ResponseEntity<String> addBookToAuthor(AuthorBookRequest request) {
        Integer authorId = authorRepository.getAuthorIdBySurname(request.getAuthor().split(" ")[0]);
        return authorBookRepository.addBookToAuthor(authorId, request.getBookId());
    }
}
