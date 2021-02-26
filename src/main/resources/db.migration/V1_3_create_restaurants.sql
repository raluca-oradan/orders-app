create table if not exists restaurants(
id             SERIAL       PRIMARY KEY,
created_at     TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
name           varchar(255) not null,
location       varchar(255) not null,
description    varchar(255) not null,
user_id        int8         references users(id)
)
