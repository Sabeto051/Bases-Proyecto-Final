-- crear la base de datos
drop schema Nisand;
create database if not exists Nisand; 
use Nisand;

-- crear las tablas 
create table if not exists Usuarios(id int auto_increment, telefono varchar(10), pwd varchar(20), primary key(id))ENGINE=INNODB;
create table if not exists Planes(id int auto_increment, duracion varchar(10), valor int, primary key(id))ENGINE=INNODB;
create table if not exists Escuelas(id int auto_increment, descripcion text, nombre varchar(20), primary key(id))ENGINE=INNODB;
create table if not exists Profesores(id int auto_increment, telefono varchar(10), pwd varchar(20), salario int, primary key(id))ENGINE=INNODB;
create table if not exists UsuariosPlanes(usuario_id int, fecha_inicio datetime, fecha_fin datetime, plan_id int, primary key(usuario_id), index p_id (plan_id), foreign key (plan_id) references Usuarios(id) on update cascade on delete cascade  )ENGINE=INNODB;
create table if not exists PlanesEscuela(plan_id int, escuela_id int, primary key(plan_id, escuela_id))ENGINE=INNODB;
create table if not exists Carreras(id int auto_increment, descripcion text, nombre varchar(20), escuela_id int, primary key (id), index e_id (escuela_id), foreign key (escuela_id) references Escuelas(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists Cursos(id int auto_increment, descripcion text, nombre varchar(20), carrera_id int, profesor_id int, primary key (id), index c_id (carrera_id), index p_id (profesor_id), foreign key(carrera_id) references Carreras(id) on update cascade on delete cascade, foreign key(profesor_id) references Profesores(id) on update cascade on delete cascade);
create table if not exists Foros(id int auto_increment, curso_id int, index c_id (curso_id), foreign key (curso_id) references Cursos(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists 