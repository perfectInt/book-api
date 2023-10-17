CREATE TABLE IF NOT EXISTS author (
    id SERIAL primary key,
    first_name VARCHAR(50) not null,
    second_name VARCHAR(50) not null,
    patronymic VARCHAR(50),
    biography TEXT
);