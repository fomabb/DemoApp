create table users
(
    id            bigserial primary key,
    name          varchar(500) not null,
    primary_email varchar(255) not null unique,
    date_of_birth date         not null,
    password      varchar(500) not null,
    unique (id)
);