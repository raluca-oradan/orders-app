create table if not exists users(
id             SERIAL      PRIMARY KEY,
created_at     TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
email          varchar(255) not null,
password       varchar(255) not null
);