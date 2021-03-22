create table if not exists customers(
id             SERIAL       PRIMARY KEY,
created_at     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
name           varchar(255) not null,
phone_number   varchar(255) not null,
address        varchar(255) not null,
user_id        int8         references users(id)
);