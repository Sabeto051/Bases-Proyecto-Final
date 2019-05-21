-- crear la base de datos
-- drop schema Nisand;
create database if not exists Nisand; 
use Nisand;

-- crear las tablas 
create table if not exists Usuarios(id int auto_increment, telefono varchar(10), pwd varchar(20), primary key(id))ENGINE=INNODB;
create table if not exists Planes(id int auto_increment, duracion varchar(10), valor int, primary key(id))ENGINE=INNODB;
create table if not exists Escuelas(id int auto_increment, descripcion text, nombre varchar(20), primary key(id))ENGINE=INNODB;
create table if not exists Profesores(id int auto_increment, telefono varchar(10), pwd varchar(20), salario int, primary key(id))ENGINE=INNODB;
create table if not exists UsuariosPlanes(usuario_id int, fecha_inicio datetime, fecha_fin datetime, plan_id int, primary key(usuario_id), index p_id (plan_id), foreign key (plan_id) references Planes(id) on update cascade on delete cascade  )ENGINE=INNODB;
create table if not exists PlanesEscuela(plan_id int, escuela_id int, primary key(plan_id, escuela_id))ENGINE=INNODB;
create table if not exists Carreras(id int auto_increment, descripcion text, nombre varchar(30), escuela_id int, primary key (id), index e_id (escuela_id), foreign key (escuela_id) references Escuelas(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists Cursos(id int auto_increment, descripcion text, nombre varchar(20), carrera_id int, profesor_id int, primary key (id), index c_id (carrera_id), index p_id (profesor_id), foreign key(carrera_id) references Carreras(id) on update cascade on delete cascade, foreign key(profesor_id) references Profesores(id) on update cascade on delete cascade);
create table if not exists Foros(id int auto_increment, curso_id int, index c_id (curso_id), primary key (id), foreign key (curso_id) references Cursos(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists Videos(id int auto_increment, curso_id int, duracion float, descripcion text, nombre varchar(20), primary key(id), index c_id (curso_id), foreign key(curso_id) references Cursos(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists Preguntas(id int auto_increment, foro_id int, usuario_id int, contenido text, fecha datetime, primary key(id), index f_id(foro_id), index u_id(usuario_id), foreign key(foro_id) references Foros(id) on update cascade on delete cascade, foreign key (usuario_id) references Usuarios(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists Respuestas(id int auto_increment, pregunta_id int, usuario_id int, contenido text, fecha datetime, puntuacion int, primary key(id), index p_id(pregunta_id), index u_id(usuario_id), foreign key(pregunta_id) references Preguntas(id) on update cascade on delete cascade, foreign key(usuario_id) references Usuarios(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists Estados(id int auto_increment, estado_tipo varchar(10), usuario_id int, index u_id(usuario_id), primary key (id), foreign key (usuario_id) references Usuarios(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists Nombres(id int auto_increment, nombre varchar(20), primary key(id));
create table if not exists Apellidos(id int auto_increment, apellido varchar(20), primary key(id));
create table if not exists FirstNameU(usuario_id int, nombre_id int, primary key(usuario_id, nombre_id), index u_id (usuario_id), index n_id (nombre_id), foreign key(usuario_id) references Usuarios(id) on update cascade on delete cascade, foreign key (nombre_id) references Nombres(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists LastNameU(usuario_id int, apellido_id int, primary key(usuario_id, apellido_id), index u_id (usuario_id), index a_id (apellido_id), foreign key(usuario_id) references Usuarios(id) on update cascade on delete cascade, foreign key (apellido_id) references Apellidos(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists FirstNameP(profesor_id int, nombre_id int, primary key(profesor_id, nombre_id), index p_id (profesor_id), index n_id (nombre_id), foreign key(profesor_id) references Profesores(id) on update cascade on delete cascade, foreign key (nombre_id) references Nombres(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists LastNameP(profesor_id int, apellido_id int, primary key(profesor_id, apellido_id), index p_id (profesor_id), index n_id (apellido_id), foreign key(profesor_id) references Profesores(id) on update cascade on delete cascade, foreign key (apellido_id) references Apellidos(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists UsuariosCursos(usuario_id int, curso_id int, primary key(usuario_id, curso_id), index u_id (usuario_id), index c_id (curso_id), foreign key (usuario_id) references Usuarios(id) on update cascade on delete cascade, foreign key (curso_id) references Cursos(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists UsuariosVideos(usuario_id int, video_id int, primary key(usuario_id, video_id), index u_id (usuario_id), index v_id (video_id), foreign key (usuario_id) references Usuarios(id) on update cascade on delete cascade, foreign key (video_id) references Videos(id) on update cascade on delete cascade) ENGINE=INNODB;

-- crear los registros default de las tablas
insert into Usuarios (telefono, pwd) values ('3045239215', 'asdf');
insert into Usuarios (telefono, pwd) values ('3022179215', 'dank');
insert into Usuarios (telefono, pwd) values ('3008106969', 'rank');
insert into Nombres (nombre) values ('El');
insert into Nombres (nombre) values ('Nicolas');
insert into Nombres (nombre) values ('Dank');
insert into Apellidos (apellido) values ('Ladron');
insert into Apellidos (apellido) values ('Perez');
insert into Apellidos (apellido) values ('Memes');
insert into FirstNameU (usuario_id, nombre_id) values ('1', '1');
insert into FirstNameU (usuario_id, nombre_id) values ('2', '2');
insert into FirstNameU (usuario_id, nombre_id) values ('3', '3');
insert into LastNameU (usuario_id, apellido_id) values ('1', '1');
insert into LastNameU (usuario_id, apellido_id) values ('2', '2');
insert into LastNameU (usuario_id, apellido_id) values ('3', '3');
insert into Escuelas (descripcion, nombre) values('Las mejores carreras son de aca', 'ingenierias');
insert into Escuelas (descripcion, nombre) values('Los que curan los enfermos', 'areas de la salud');
insert into Carreras (descripcion, nombre, escuela_id) values ('La mejor ingenieria','Ingenieria de Sistemas','1');
insert into Carreras (descripcion, nombre, escuela_id) values ('una ingenieria ahi','Ingenieria Administrativa','1');
insert into Carreras (descripcion, nombre, escuela_id) values ('Los que ganan mas plata','Medicina','2');
insert into Profesores (telefono, pwd, salario) values ('3001003020','profe','100000');
insert into Profesores (telefono, pwd, salario) values ('3003003030','password','20000');
insert into Nombres (nombre) values ('Ale');
insert into Nombres (nombre) values ('Johan');
insert into Apellidos (apellido) values ('Paper');
insert into Apellidos (apellido) values ('Velez');
insert into FirstNameP (profesor_id, nombre_id) values ('1', '1');
insert into FirstNameP (profesor_id, nombre_id) values ('2', '2');
insert into LastNameP (profesor_id, apellido_id) values ('1', '1');
insert into LastNameP (profesor_id, apellido_id) values ('2', '2');




