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