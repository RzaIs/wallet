
create table card
(
    id serial not null primary key,
    pan text not null unique,
    customer_id bigint not null,
    balance decimal not null,
    created_at timestamp without time zone default now() not null
)
