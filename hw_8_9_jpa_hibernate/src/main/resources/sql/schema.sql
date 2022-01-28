CREATE TABLE IF NOT EXISTS students
(
    id
    BIGINT
    auto_increment
    PRIMARY
    KEY,
    first_name
    varchar
(
    255
) NOT NULL,
    last_name varchar
(
    255
) NOT NULL,
    age int NOT NULL,
    email varchar
(
    255
) NOT NULL
    );

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

CREATE TABLE IF NOT EXISTS students_to_groups
(
    group_id
    bigint
    NOT
    NULL,
    student_id
    bigint
    NOT
    NULL,
    PRIMARY
    KEY
(
    group_id,
    student_id
),
    CONSTRAINT fk_group FOREIGN KEY
(
    group_id
)
    REFERENCES students_group
(
    id
)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
    CONSTRAINT fk_student FOREIGN KEY
(
    student_id
)
    REFERENCES students
(
    id
)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
    );