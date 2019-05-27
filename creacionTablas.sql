-- crear la base de datos
drop schema Nisand;
create database if not exists Nisand; 
use Nisand;

-- crear las tablas 
create table if not exists usuarios(id int auto_increment, telefono varchar(10), pwd varchar(20), primary key(id))ENGINE=INNODB;
create table if not exists planes(id int auto_increment, duracion varchar(10), valor int, primary key(id))ENGINE=INNODB;
create table if not exists escuelas(id int auto_increment, nombre varchar(20),descripcion text, primary key(id))ENGINE=INNODB;
create table if not exists profesores(id int auto_increment, telefono varchar(10), pwd varchar(20), salario int, primary key(id))ENGINE=INNODB;
create table if not exists usuariosplanes(usuario_id int, fecha_inicio datetime, fecha_fin datetime, plan_id int, primary key(usuario_id), index p_id (plan_id), foreign key (plan_id) references planes(id) on update cascade on delete cascade  )ENGINE=INNODB;
create table if not exists planesescuela(plan_id int, escuela_id int, primary key(plan_id, escuela_id))ENGINE=INNODB;
create table if not exists carreras(id int auto_increment, nombre varchar(30), descripcion text,escuela_id int, primary key (id), index e_id (escuela_id), foreign key (escuela_id) references escuelas(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists cursos(id int auto_increment, nombre varchar(20), descripcion text, carrera_id int, profesor_id int, primary key (id), index c_id (carrera_id), index p_id (profesor_id), foreign key(carrera_id) references carreras(id) on update cascade on delete cascade, foreign key(profesor_id) references profesores(id) on update cascade on delete cascade);
create table if not exists foros(id int auto_increment, nombre varchar(20), curso_id int, index c_id (curso_id), primary key (id), foreign key (curso_id) references cursos(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists videos(id int auto_increment, curso_id int, nombre varchar(20), duracion float, descripcion text, primary key(id), index c_id (curso_id), foreign key(curso_id) references cursos(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists preguntas(id int auto_increment, foro_id int, usuario_id int, contenido text, fecha datetime, primary key(id), index f_id(foro_id), index u_id(usuario_id), foreign key(foro_id) references foros(id) on update cascade on delete cascade, foreign key (usuario_id) references usuarios(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists respuestas(id int auto_increment, pregunta_id int, usuario_id int, contenido text, fecha datetime, puntuacion int, primary key(id), index p_id(pregunta_id), index u_id(usuario_id), foreign key(pregunta_id) references preguntas(id) on update cascade on delete cascade, foreign key(usuario_id) references usuarios(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists estados(id int auto_increment, estado_tipo varchar(10), usuario_id int, index u_id(usuario_id), primary key (id), foreign key (usuario_id) references usuariosplanes(usuario_id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists nombres(id int auto_increment, nombre varchar(20), primary key(id));
create table if not exists apellidos(id int auto_increment, apellido varchar(20), primary key(id));
create table if not exists firstnameu(usuario_id int, nombre_id int, primary key(usuario_id, nombre_id), index u_id (usuario_id), index n_id (nombre_id), foreign key(usuario_id) references usuarios(id) on update cascade on delete cascade, foreign key (nombre_id) references nombres(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists lastnameu(usuario_id int, apellido_id int, primary key(usuario_id, apellido_id), index u_id (usuario_id), index a_id (apellido_id), foreign key(usuario_id) references usuarios(id) on update cascade on delete cascade, foreign key (apellido_id) references apellidos(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists firstnamep(profesor_id int, nombre_id int, primary key(profesor_id, nombre_id), index p_id (profesor_id), index n_id (nombre_id), foreign key(profesor_id) references profesores(id) on update cascade on delete cascade, foreign key (nombre_id) references nombres(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists lastnamep(profesor_id int, apellido_id int, primary key(profesor_id, apellido_id), index p_id (profesor_id), index n_id (apellido_id), foreign key(profesor_id) references profesores(id) on update cascade on delete cascade, foreign key (apellido_id) references apellidos(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists usuarioscursos(usuario_id int, curso_id int, primary key(usuario_id, curso_id), index u_id (usuario_id), index c_id (curso_id), foreign key (usuario_id) references usuarios(id) on update cascade on delete cascade, foreign key (curso_id) references cursos(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists usuariosvideos(usuario_id int, video_id int, primary key(usuario_id, video_id), index u_id (usuario_id), index v_id (video_id), foreign key (usuario_id) references usuarios(id) on update cascade on delete cascade, foreign key (video_id) references videos(id) on update cascade on delete cascade) ENGINE=INNODB;
create table if not exists ulike(usuario_id int, respuesta_id int, primary key(usuario_id, respuesta_id), foreign key (usuario_id) references usuarios(id) on update cascade on delete cascade, foreign key (respuesta_id) references respuestas(id) on update cascade on delete cascade) ENGINE=INNODB;
-- crear los registros default de las tablas

-- Usuarios 
insert into usuarios (telefono, pwd) values ('3045239215', 'asdf');
insert into usuarios (telefono, pwd) values ('3022179215', 'dank');
insert into usuarios (telefono, pwd) values ('3008106969', 'rank');
insert into nombres (nombre) values ('El');
insert into nombres (nombre) values ('Nicolas');
insert into nombres (nombre) values ('Dank');
insert into apellidos (apellido) values ('Ladron');
insert into apellidos (apellido) values ('Perez');
insert into apellidos (apellido) values ('Memes');
insert into firstnameu (usuario_id, nombre_id) values ('1', '1');
insert into firstnameu (usuario_id, nombre_id) values ('2', '2');
insert into firstnameu (usuario_id, nombre_id) values ('3', '3');
insert into lastnameu (usuario_id, apellido_id) values ('1', '1');
insert into lastnameu (usuario_id, apellido_id) values ('2', '2');
insert into lastnameu (usuario_id, apellido_id) values ('3', '3');

-- Escuelas
insert into escuelas (descripcion, nombre) values('Las mejores carreras son de aca', 'ingenierias');
insert into escuelas (descripcion, nombre) values('Los que curan los enfermos', 'areas de la salud');
insert into escuelas (descripcion, nombre) values('No hacen nada', 'administrativa');

-- Carreras
insert into carreras (descripcion, nombre, escuela_id) values ('La mejor ingenieria','Ingenieria de Sistemas','1');
insert into carreras (descripcion, nombre, escuela_id) values ('una ingenieria ahi','Ingenieria Administrativa','1');
insert into carreras (descripcion, nombre, escuela_id) values ('Los que ganan mas plata','Medicina','2');

-- Profesores
insert into profesores (telefono, pwd, salario) values ('3001003020','profe','100000');
insert into profesores (telefono, pwd, salario) values ('3003003030','password','20000');
insert into nombres (nombre) values ('Ale');
insert into nombres (nombre) values ('Johan');
insert into apellidos (apellido) values ('Paper');
insert into apellidos (apellido) values ('Velez');
insert into firstnamep (profesor_id, nombre_id) values ('1', '4');
insert into firstnamep (profesor_id, nombre_id) values ('2', '5');
insert into lastnamep (profesor_id, apellido_id) values ('1', '4');
insert into lastnamep (profesor_id, apellido_id) values ('2', '5');

-- Cursos
insert into cursos (carrera_id, profesor_id, descripcion, nombre) values ('1','1','Una materia mela','Bases de Datos');
insert into cursos (carrera_id, profesor_id, descripcion, nombre) values ('2','2','Una materia regular','Admin General');
insert into cursos (carrera_id, profesor_id, descripcion, nombre) values ('1','2','Una materia mela otra vez','Proyecto 2');
insert into usuarioscursos(curso_id, usuario_id) values ('1','1');
insert into usuarioscursos(curso_id, usuario_id) values ('1','2');
insert into usuarioscursos(curso_id, usuario_id) values ('1','3');
insert into usuarioscursos(curso_id, usuario_id) values ('2','1');
insert into usuarioscursos(curso_id, usuario_id) values ('2','2');
insert into usuarioscursos(curso_id, usuario_id) values ('3','1');

-- Foros
insert into foros (curso_id, nombre) values ('1','Venecas');
insert into foros (curso_id, nombre) values ('2','Trump Hentai');
insert into foros (curso_id, nombre) values ('3','Kim Posible');

-- Videos
insert into videos (curso_id, duracion, descripcion, nombre) values ('1','123.2','aprendiendo mysql','BD_1');
insert into videos (curso_id, duracion, descripcion, nombre) values ('1','123123.2','aprendiendo mas mysql','BD_2');
insert into videos (curso_id, duracion, descripcion, nombre) values ('2','12233.2','aprendiendo basura','admin1');
insert into videos (curso_id, duracion, descripcion, nombre) values ('3','1.2','aprendiendo comida','biologia');
insert into usuariosvideos(video_id, usuario_id) values ('1','1');
insert into usuariosvideos(video_id, usuario_id) values ('1','2');
insert into usuariosvideos(video_id, usuario_id) values ('2','3');
insert into usuariosvideos(video_id, usuario_id) values ('2','1');

-- Preguntas
insert into preguntas(foro_id, usuario_id, contenido, fecha) values ('1','1','asdf','2017-06-11 11:11:11');
insert into preguntas(foro_id, usuario_id, contenido, fecha) values ('2','1','Que dia es hoy?','2017-06-12 11:11:11');
insert into preguntas(foro_id, usuario_id, contenido, fecha) values ('3','2','Auxilio jeje','2017-06-11 11:11:11');
insert into respuestas(pregunta_id, usuario_id, contenido, fecha, puntuacion) values ('1','3','fdsa','2017-07-11 11:11:11','0');
insert into respuestas(pregunta_id, usuario_id, contenido, fecha, puntuacion) values ('1','2','Domingo','2017-07-11 11:11:11','0');
insert into respuestas(pregunta_id, usuario_id, contenido, fecha, puntuacion) values ('3','2','Auxilio con que','2017-07-11 11:11:11','0');

-- Planes
insert into planes(duracion, valor) values ('1año','1000000');
insert into planes(duracion, valor) values ('3meses','400000');
insert into planes(duracion, valor) values ('1mes','150000');

-- Usuarios-Planes
insert into usuariosplanes(usuario_id, plan_id, fecha_inicio, fecha_fin) values ('1','1','2017-06-11 11:11:11','2018-06-11 11:11:11');
insert into usuariosplanes(usuario_id, plan_id, fecha_inicio, fecha_fin) values ('2','2','2017-09-11 11:11:11','2017-12-11 11:11:11');
insert into usuariosplanes(usuario_id, plan_id, fecha_inicio, fecha_fin) values ('3','3','2017-06-11 11:11:11','2017-07-11 11:11:11');
insert into estados(usuario_id, estado_tipo) values ('1','activo');
insert into estados(usuario_id, estado_tipo) values ('2','inactivo');
insert into estados(usuario_id, estado_tipo) values ('3','activo');

-- Planes-Escuela
insert into planesescuela(plan_id, escuela_id) values ('1', '1');
insert into planesescuela(plan_id, escuela_id) values ('2', '3');
insert into planesescuela(plan_id, escuela_id) values ('3', '2');

--ULike
insert into ulike(usuario_id,respuesta_id) values(2,1);
update respuestas set puntuacion=1 where id=1;
                                                                                              
                
-- test








