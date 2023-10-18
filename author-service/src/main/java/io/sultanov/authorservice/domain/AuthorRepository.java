package io.sultanov.authorservice.domain;

import io.sultanov.authorservice.domain.tables.pojos.Author;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class AuthorRepository {

    @Autowired
    private DSLContext dsl;

    public void insertAuthor(Author author) {
        dsl.insertInto(Tables.AUTHOR)
                .set(Tables.AUTHOR.FIRST_NAME, author.getFirstName())
                .set(Tables.AUTHOR.SECOND_NAME, author.getSecondName())
                .set(Tables.AUTHOR.PATRONYMIC, author.getPatronymic())
                .set(Tables.AUTHOR.BIOGRAPHY, author.getBiography())
                .execute();
    }

    public Record getAuthorById(Integer id) {
        return dsl.select(
                Tables.AUTHOR.ID, Tables.AUTHOR.FIRST_NAME, Tables.AUTHOR.SECOND_NAME,
                Tables.AUTHOR.PATRONYMIC, Tables.AUTHOR.BIOGRAPHY
        ).from(Tables.AUTHOR).where(Tables.AUTHOR.ID.equal(id)).fetchOne();
    }

    public Boolean existsBySurname(String author) {
        Record record = dsl.select(
                Tables.AUTHOR.ID, Tables.AUTHOR.FIRST_NAME, Tables.AUTHOR.SECOND_NAME,
                Tables.AUTHOR.PATRONYMIC, Tables.AUTHOR.BIOGRAPHY
        ).from(Tables.AUTHOR).where(Tables.AUTHOR.SECOND_NAME.equal(author)).fetchOne();
        return record != null;
    }

    public Integer getAuthorIdBySurname(String author) {
        Record record = dsl.select(
                Tables.AUTHOR.ID
        ).from(Tables.AUTHOR).where(Tables.AUTHOR.SECOND_NAME.equal(author)).fetchOne();
        if (record == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return record.get("id", Integer.class);
    }
}
