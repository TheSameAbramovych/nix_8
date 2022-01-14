CREATE TABLE IF NOT EXISTS students
(
    id         BIGINT auto_increment PRIMARY KEY,
    first_name varchar(255) NOT NULL,
    last_name  varchar(255) NOT NULL,
    age        int          NOT NULL,
    email      varchar(255) NOT NULL
);

