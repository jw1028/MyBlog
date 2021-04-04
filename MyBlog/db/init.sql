-- 创建数据库
drop database if exists blog;
create database blog character set utf8mb4;

-- 注意：不能省略
use blog;

-- 创建用户表
drop table if exists user;
create table user(
        id int primary key auto_increment,
        create_time datetime default now(),
        update_time datetime default now(),
        username varchar(50) not null,
        password varchar(32) not null,
        state int default 1
);

-- 创建文章表
drop table if exists article;
create table article(
        id int primary key auto_increment,
        create_time datetime default now(),
        update_time datetime default now(),
        title varchar(100) not null,
        content text not null,
        rcount int default 1,
        state int default 1,
        user_id int not null
);


