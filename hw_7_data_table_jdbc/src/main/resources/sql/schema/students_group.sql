CREATE TABLE IF NOT EXISTS students_group
(
    id
    BIGINT
    auto_increment
    PRIMARY
    KEY,
    name
    varchar
(
    255
) NOT NULL,
    headman bigint,
    CONSTRAINT fk_headman FOREIGN KEY
(
    headman
)
    REFERENCES students
(
    id
)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
    );