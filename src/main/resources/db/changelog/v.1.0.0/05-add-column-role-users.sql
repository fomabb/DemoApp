alter table users
    add column role varchar(20) not null default 'ROLE_USER';