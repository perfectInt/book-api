CREATE TABLE IF NOT EXISTS author_book (
    author_id SERIAL not null,
    book_id SERIAL not null,
    FOREIGN KEY (author_id) REFERENCES author (id)
)