drop table todo if exists;
create table todo (
  id int primary key auto_increment,
  task varchar(128) not null,
  created BIGINT not null,
  completed BIGINT not null
);