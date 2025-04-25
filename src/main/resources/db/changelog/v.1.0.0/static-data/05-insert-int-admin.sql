insert into users (name, primary_email, date_of_birth, password, role)
VALUES ('admin',
        'admin@gmail.com',
        '1212-12-12',
        '$2a$10$Ts70uPxEp1E1dFW4D/H1XeYdZAA7QdLLRUR/4bvO6ey/qYQ24DMZ6',
        'ROLE_ADMIN');

insert into phone_data (user_id, phone)
values (6, '79121212121');

insert into email_data (user_id, email)
values (6, 'admin@gmail.com');

insert into accounts (user_id, balance, actual_balance)
values (6, '12.21', '12.21');