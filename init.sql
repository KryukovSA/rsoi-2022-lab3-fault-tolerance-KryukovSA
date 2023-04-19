CREATE TABLE reservation
(
    id              SERIAL PRIMARY KEY,
    reservation_uid uuid UNIQUE NOT NULL,
    username        VARCHAR(80) NOT NULL,
    book_uid        uuid        NOT NULL,
    library_uid     uuid        NOT NULL,
    status          VARCHAR(20) NOT NULL
        CHECK (status IN ('RENTED', 'RETURNED', 'EXPIRED')),
    start_date      TIMESTAMP   NOT NULL,
    till_date       TIMESTAMP   NOT NULL
);

CREATE TABLE library
(
    id          SERIAL PRIMARY KEY,
    library_uid uuid UNIQUE  NOT NULL,
    name        VARCHAR(80)  NOT NULL,
    city        VARCHAR(255) NOT NULL,
    address     VARCHAR(255) NOT NULL
);

CREATE TABLE books
(
    id        SERIAL PRIMARY KEY,
    book_uid  uuid UNIQUE  NOT NULL,
    name      VARCHAR(255) NOT NULL,
    author    VARCHAR(255),
    genre     VARCHAR(255),
    condition VARCHAR(20) DEFAULT 'EXCELLENT'
        CHECK (condition IN ('EXCELLENT', 'GOOD', 'BAD'))
);

CREATE TABLE library_books
(
    id       SERIAL PRIMARY KEY,
    book_id         INT REFERENCES books (id),
    library_id      INT REFERENCES library (id),
    available_count INT NOT NULL
);


CREATE TABLE rating
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(80) NOT NULL,
    stars    INT         NOT NULL
        CHECK (stars BETWEEN 0 AND 100)
);


insert into library (id, library_uid, name, city, address)
values (1, '83575e12-7ce0-48ee-9931-51919ff3c9ee',
        'Библиотека имени 7 Непьющих',
        'Москва',
        '2-я Бауманская ул., д.5, стр.1');

insert into books (id, book_uid, name, author, genre, condition)
values (1, 'f7cdc58f-2caf-4b15-9727-f89dcc629b27',
        'Краткий курс C++ в 7 томах',
        'Бьерн Страуструп',
        'Научная фантастика',
        'EXCELLENT');

insert into library_books (book_id, library_id, available_count)
values (1, 1, 1);


insert into rating (id, username, stars)
values (1, 'Test Max', 75)