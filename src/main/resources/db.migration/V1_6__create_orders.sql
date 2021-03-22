create table if not exists orders(
id                   SERIAL               PRIMARY KEY,
created_at           TIMESTAMP            DEFAULT CURRENT_TIMESTAMP,
order_no             int8                 not null,
order_description    varchar(255)         not null,
info                 varchar(255)         not null,
customer_id          int8                 references customers(id),
restaurant_id        int8                 references restaurants(id)
)