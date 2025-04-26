create table accounts
(
    id             bigserial primary key,
    user_id        bigint  not null unique,
    balance        decimal not null check ( BALANCE >= 0 ),
    actual_balance decimal not null default 0.00,
    foreign key (user_id) references users (id) on delete cascade
);