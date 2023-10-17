CREATE TABLE IF NOT EXISTS notification (
    id SERIAL primary key,
    message VARCHAR(256),
    date_of_notification TIMESTAMPTZ
)