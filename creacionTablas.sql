-- crear la base de datos
drop schema Nisand;
create database if not exists Nisand; 
use Nisand;

-- crear las tablas que no tienen FK
create table if not exists Usuarios(id int auto_increment, telefono varchar(10), pwd varchar(20), primary key(id))ENGINE=INNODB;
create table if not exists Planes(id int auto_increment, duracion varchar(10), valor int, primary key(id))ENGINE=INNODB;
create table if not exists Escuelas(id int auto_increment, descripcion text, nombre varchar(20), primary key(id))ENGINE=INNODB;
create table if not exists Profesores(id int auto_increment, telefono varchar(10), pwd varchar(20), salario int, primary key(id))ENGINE=INNODB;


-- MI DUDA ES AQUI, GRACIAS JEJE 
create table if not exists UsuariosPlanes(usuario_id int auto_increment, fecha_inicio datetime, fecha_fin datetime, plan_id int, primary key(usuario_id), index p_id (plan_id), foreign key (plan_id) references Usuarios(id) on update cascade on delete cascade  )ENGINE=INNODB;

show indexes from UsuariosPlanes;