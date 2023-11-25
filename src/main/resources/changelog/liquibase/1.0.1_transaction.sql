
create table _transaction
(
    id serial not null primary key,
    _type text not null,
    amount decimal not null,
    card_id bigint not null references card(id),
    has_cashback boolean not null,
    created_at timestamp without time zone default now() not null
)
