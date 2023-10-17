package io.sultanov.authorservice.domain;

import io.sultanov.authorservice.domain.tables.pojos.Author;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record5;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.CompletableFuture;

@Service
public class AuthorService {

    @Autowired
    private DSLContext dsl;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void insertAuthor(Author author) {
        dsl.insertInto(Tables.AUTHOR)
                .set(Tables.AUTHOR.FIRST_NAME, author.getFirstName())
                .set(Tables.AUTHOR.SECOND_NAME, author.getSecondName())
                .set(Tables.AUTHOR.PATRONYMIC, author.getPatronymic())
                .set(Tables.AUTHOR.BIOGRAPHY, author.getBiography())
                .execute();

        sendMessageToAuthorNotificationTopic("Inserted new author: " + author.toString());
    }

    public AuthorDto getAuthorById(Integer id) {
        Record record = dsl.select(
                Tables.AUTHOR.ID, Tables.AUTHOR.FIRST_NAME, Tables.AUTHOR.SECOND_NAME,
                Tables.AUTHOR.PATRONYMIC, Tables.AUTHOR.BIOGRAPHY
        ).from(Tables.AUTHOR).where(Tables.AUTHOR.ID.equal(id)).fetchOne();

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
}
