create table email_data
(
    id      BIGSERIAL primary key,
    user_id BIGINT       not null,
    email   VARCHAR(200) not null unique,
    foreign key (user_id) references users (id) on delete cascade
);