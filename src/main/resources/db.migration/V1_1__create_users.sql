CREATE TABLE IF NOT EXISTS users(
 created_at  timestamp   default current_timestamp,
 id          SERIAL      PRIMARY KEY,
 email       varchar(255) not null,
 password    varchar(255) not null,
 role        varchar(255) not null
);