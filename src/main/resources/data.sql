insert into User (id, username, password) values (1,'basico','$2a$10$O6jDBlpb/.6Hr2fMn5vKmeOJ71jvbtJE/koxj5rOcmwIeNq1QwvYi');
insert into User (id, username, password) values (2,'admin','$2a$10$O6jDBlpb/.6Hr2fMn5vKmeOJ71jvbtJE/koxj5rOcmwIeNq1QwvYi');
insert into User (id, username, password) values (3,'super','$2a$10$O6jDBlpb/.6Hr2fMn5vKmeOJ71jvbtJE/koxj5rOcmwIeNq1QwvYi');

insert into Role (id, role) values (1,'ROLE_BASIC');
insert into Role (id, role) values (2,'ROLE_ADMIN');
insert into Role (id, role) values (3,'ROLE_SUPER');

insert into user_role (id_user, id_role) values (1,1);
insert into user_role (id_user, id_role) values (2,1);
insert into user_role (id_user, id_role) values (2,2);
insert into user_role (id_user, id_role) values (3,3);