create table roles_privileges (role_id bigint not null, privilege_id bigint not null) engine=InnoDB;
create table users_roles (user_id bigint not null, role_id bigint not null) engine=InnoDB;
alter table roles_privileges add constraint FK5yjwxw2gvfyu76j3rgqwo685u foreign key (privilege_id) references privilege (id);
alter table roles_privileges add constraint FK9h2vewsqh8luhfq71xokh4who foreign key (role_id) references role (id);
alter table users_roles add constraint FKt4v0rrweyk393bdgt107vdx0x foreign key (role_id) references role (id);
alter table users_roles add constraint FKgd3iendaoyh04b95ykqise6qh foreign key (user_id) references user (id);
