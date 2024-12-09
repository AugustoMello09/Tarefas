create table tb_cargo (id bigint not null auto_increment, authority varchar(255) not null, primary key (id));
create table tb_tarefa (cost decimal(38,2), due_date date, position integer, id bigint not null auto_increment, usuario_id binary(16), name varchar(255), primary key (id));
create table tb_usuario (id binary(16) not null, email varchar(255), name varchar(255), password varchar(255), primary key (id));
create table tb_usuario_cargo (cargo_id bigint not null, usuario_id binary(16) not null, primary key (cargo_id, usuario_id));
alter table tb_tarefa add constraint UK_9nq49bkl552f2pp9p0cyehulr unique (name);
alter table tb_usuario add constraint UK_spmnyb4dsul95fjmr5kmdmvub unique (email);
alter table tb_tarefa add constraint FKedirc16whwdanrdo0a20m1wre foreign key (usuario_id) references tb_usuario (id);
alter table tb_usuario_cargo add constraint FKfoxfmrp32u59p9yvguydp9ybj foreign key (cargo_id) references tb_cargo (id);
alter table tb_usuario_cargo add constraint FKdaew2172j172duhsiyma5rfq5 foreign key (usuario_id) references tb_usuario (id);