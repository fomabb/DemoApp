create table phone_data
(
    id      bigserial primary key,
    user_id bigint      not null,
    phone   varchar(13) not null unique,
    foreign key (user_id) references users (id) on delete cascade
);