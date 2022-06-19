CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE SCHEMA IF NOT EXISTS password_manager;
CREATE TABLE IF NOT EXISTS password_manager."user"
(
    id         uuid    NOT NULL DEFAULT gen_random_uuid(),
    username   varchar NOT NULL,
    "password" varchar NOT NULL,
    CONSTRAINT user_pk PRIMARY KEY (id),
    CONSTRAINT user_un UNIQUE (username)
);