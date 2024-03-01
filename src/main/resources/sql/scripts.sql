CREATE DATABASE `BANK`;

USE `BANK`;

CREATE TABLE `USERS` (
                       `ID` int not null auto_increment,
                       `USERNAME` VARCHAR(45) not null,
                       `PASSWORD` VARCHAR(45) NOT NULL,
                       `ENABLED` int not null,
                       primary key(`ID`)
);


CREATE TABLE AUTHORITIES (
                             `ID` int not null auto_increment,
                             `USERNAME` VARCHAR(45) NOT NULL,
                             `AUTHORITY` VARCHAR(45) NOT NULL,
                             primary key(`ID`)
);

CREATE TABLE `customer` (
                            `id` INT auto_increment primary KEY,
                            `email` VARCHAR(45) NOT NULL,
                            `password` VARCHAR(200) NOT NULL,
                            `role` VARCHAR(45) NOT NULL);


insert INTO `customer`(`email`, `password`, `role`) VALUES ('admin@gmail.com', 'admin', 'admin');
insert INTO `customer`(`email`, `password`, `role`) VALUES ('user@gmail.com', 'user', 'user');

insert ignore into users values (null, 'user', 'user', '1');
insert ignore into AUTHORITIES values (null, 'user', 'write');