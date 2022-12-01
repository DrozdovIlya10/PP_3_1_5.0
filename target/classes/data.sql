insert into t_role(id, name)
values (1, 'ROLE_USER')
    ON DUPLICATE KEY UPDATE name = 'ROLE_USER';
insert into t_role(id, name)
values (2, 'ROLE_ADMIN')
    ON DUPLICATE KEY UPDATE name = 'ROLE_ADMIN';
insert into t_user(id, username,password,lastname,email,age)
values (1, 'user', '$2a$10$/jbAthSR7KPoYjp.s5yqMeBwg7Mvgc8imNx5ntCcDq.duNqjiwgKa','user','user@mail.ru',0)
    ON DUPLICATE KEY UPDATE username = 'user';
insert into t_user(id, username,password,lastname,email,age)
values (2, 'admin', '$2a$10$/jbAthSR7KPoYjp.s5yqMeBwg7Mvgc8imNx5ntCcDq.duNqjiwgKa','admin','admin@mail.ru',0)
    ON DUPLICATE KEY UPDATE username = 'admin';
insert into t_user_roles (user_id, roles_id)
VALUES (1, 1)
    ON DUPLICATE KEY UPDATE user_id = 1;
insert into t_user_roles (user_id, roles_id)
VALUES (2, 2)
    ON DUPLICATE KEY UPDATE user_id = 2;
insert into t_user_roles (user_id, roles_id)
VALUES (2, 1)
    ON DUPLICATE KEY UPDATE user_id  = 2,
                         roles_id = 1;
update hibernate_sequence
set next_val = 3
where next_val = 1;


