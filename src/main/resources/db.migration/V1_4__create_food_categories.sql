create table if not exists food_categories(
id                   SERIAL       PRIMARY KEY,
created_at           TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
name                 varchar(255) not null,
restaurant_id        int8   references restaurants(id)
)