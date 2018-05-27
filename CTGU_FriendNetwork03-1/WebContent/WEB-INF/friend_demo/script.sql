create table friend_user(
id bigint primary key auto_increment,
username varchar(20) unique,
password varchar(10),
name varchar(20),
age int(3),
gendar char(1),
phone varchar(20));

create table friend_pic(
id bigint primary key auto_increment,
picName varchar(100),
userId bigint);

delete from friend_user;

delete from friend_pic;