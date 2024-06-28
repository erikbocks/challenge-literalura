CREATE TABLE public.authors (
    author_id bigserial NOT NULL,
    "name" varchar(255) NOT NULL,
    birth_year int4 NOT NULL,
    death_year int4 NOT NULL,
    CONSTRAINT authors_pk PRIMARY KEY (author_id),
    CONSTRAINT authors_unique UNIQUE (name)
);

CREATE TABLE public.books (
    book_id bigserial NOT NULL,
    title varchar(255) NOT NULL,
    "language" varchar(255) NOT NULL,
    downloads int4 NOT NULL,
    author_id int8 NULL,
    CONSTRAINT books_pk PRIMARY KEY (book_id),
    CONSTRAINT books_unique UNIQUE (title)
);

ALTER TABLE public.books ADD CONSTRAINT books_authors_fk FOREIGN KEY (author_id) REFERENCES public.authors(author_id);