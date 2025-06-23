-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS YiWaiPi;
USE YiWaiPi;

-- Tabla Grupo
CREATE TABLE GRUPO (
    nombre VARCHAR(100) PRIMARY KEY,
    fecha_debut DATE,
    listeners_apple INT,
    listeners_spotify INT,
    nombre_fandom VARCHAR(100)
);

-- Tabla Album
CREATE TABLE ALBUM (
    nombre VARCHAR(100),
    fecha_lanzamiento DATE,
    concepto VARCHAR(200),
    duracion_total INT NOT NULL,
    tipo ENUM('LP', 'EP') NOT NULL,
    PRIMARY KEY (nombre, fecha_lanzamiento)
);

-- Tabla Cancion
CREATE TABLE CANCION (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    genero VARCHAR(50),
    reproducciones_apple BIGINT NOT NULL,
    reproducciones_spotify BIGINT NOT NULL
);

-- Relación Contiene
CREATE TABLE CONTIENE (
    album_nombre VARCHAR(100),
    album_fecha_lanzamiento DATE,
    cancion_id INT,
    PRIMARY KEY (album_nombre, album_fecha_lanzamiento, cancion_id),
    FOREIGN KEY (album_nombre, album_fecha_lanzamiento) REFERENCES ALBUM(nombre, fecha_lanzamiento),
    FOREIGN KEY (cancion_id) REFERENCES CANCION(id)
);

-- Tabla Integrante
CREATE TABLE INTEGRANTE (
    rut VARCHAR(20) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    animal_espiritual VARCHAR(50)
);

-- Tabla Zodiaco
CREATE TABLE ZODIACO (
    fecha_nacimiento DATE PRIMARY KEY,
    edad INT NOT NULL,
    signo_zodiacal VARCHAR(50) NOT NULL
);

-- Relación Signo
ALTER TABLE INTEGRANTE
ADD CONSTRAINT FK_SIGNO_ZODIACO
FOREIGN KEY (fecha_nacimiento) REFERENCES ZODIACO(fecha_nacimiento);

-- Subtipos de Integrante
CREATE TABLE RAPERO (
    integrante_rut VARCHAR(20) PRIMARY KEY,
    rapidez INT NOT NULL,
    FOREIGN KEY (integrante_rut) REFERENCES INTEGRANTE(rut)
);

CREATE TABLE CANTANTE (
    integrante_rut VARCHAR(20) PRIMARY KEY,
    talento INT NOT NULL,
    FOREIGN KEY (integrante_rut) REFERENCES INTEGRANTE(rut)
);

CREATE TABLE BAILARIN (
    integrante_rut VARCHAR(20) PRIMARY KEY,
    talla_zapatillas INT NOT NULL,
    FOREIGN KEY (integrante_rut) REFERENCES INTEGRANTE(rut)
);

-- Relación Pertenece_a
CREATE TABLE PERTENECE_A (
    integrante_rut VARCHAR(20),
    grupo_nombre VARCHAR(100),
    PRIMARY KEY (integrante_rut, grupo_nombre),
    FOREIGN KEY (integrante_rut) REFERENCES INTEGRANTE(rut),
    FOREIGN KEY (grupo_nombre) REFERENCES GRUPO(nombre)
);

-- Relación Compone (Grupo y Cancion)
CREATE TABLE COMPONE_GRUPO (
    grupo_nombre VARCHAR(100),
    cancion_id INT,
    PRIMARY KEY (grupo_nombre, cancion_id),
    FOREIGN KEY (grupo_nombre) REFERENCES GRUPO(nombre),
    FOREIGN KEY (cancion_id) REFERENCES CANCION(id)
);

-- Relación Compone (Integrante y Cancion)
CREATE TABLE COMPONE_INTEGRANTE (
    integrante_rut VARCHAR(20),
    cancion_id INT,
    PRIMARY KEY (integrante_rut, cancion_id),
    FOREIGN KEY (integrante_rut) REFERENCES INTEGRANTE(rut),
    FOREIGN KEY (cancion_id) REFERENCES CANCION(id)
);

-- Relación Colaboración
CREATE TABLE COLABORACION (
    grupo_principal_nombre VARCHAR(100),
    grupo_feat_nombre VARCHAR(100),
    fecha DATE NOT NULL,
    PRIMARY KEY (grupo_principal_nombre, grupo_feat_nombre),
    FOREIGN KEY (grupo_principal_nombre) REFERENCES GRUPO(nombre),
    FOREIGN KEY (grupo_feat_nombre) REFERENCES GRUPO(nombre)
);

-- Tabla Gira
CREATE TABLE GIRA (
    nombre VARCHAR(100) PRIMARY KEY,
    anho_realizacion YEAR NOT NULL
);

-- Tabla Pais
CREATE TABLE PAIS (
    nombre VARCHAR(100) PRIMARY KEY,
    num_ciudades INT NOT NULL,
    porcentaje_kpop_listeners DECIMAL(5,2)
);

-- Tabla Concierto
CREATE TABLE CONCIERTO (
    hora_inicio TIME,
    fecha DATE,
    num_participantes INT NOT NULL,
    bool_sold_out BOOLEAN NOT NULL,
    gira_nombre VARCHAR(100),
    PRIMARY KEY (hora_inicio, fecha),
    FOREIGN KEY (gira_nombre) REFERENCES GIRA(nombre)
);

-- Tabla Merchandise
CREATE TABLE MERCHANDISE (
    id INT AUTO_INCREMENT PRIMARY KEY,
    producto VARCHAR(100) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    bool_exclusividad BOOLEAN NOT NULL
);

-- Relación Realiza
CREATE TABLE REALIZA (
    gira_nombre VARCHAR(100),
    grupo_nombre VARCHAR(100),
    PRIMARY KEY (gira_nombre, grupo_nombre),
    FOREIGN KEY (gira_nombre) REFERENCES GIRA(nombre),
    FOREIGN KEY (grupo_nombre) REFERENCES GRUPO(nombre)
);

-- Relación Por
CREATE TABLE POR (
    gira_nombre VARCHAR(100),
    pais_nombre VARCHAR(100),
    PRIMARY KEY (gira_nombre, pais_nombre),
    FOREIGN KEY (gira_nombre) REFERENCES GIRA(nombre),
    FOREIGN KEY (pais_nombre) REFERENCES PAIS(nombre)
);

-- Relación Cuenta_con
CREATE TABLE CUENTA_CON (
    gira_nombre VARCHAR(100),
    cancion_id INT,
    PRIMARY KEY (gira_nombre, cancion_id),
    FOREIGN KEY (gira_nombre) REFERENCES GIRA(nombre),
    FOREIGN KEY (cancion_id) REFERENCES CANCION(id)
);

-- Relación Tiene
ALTER TABLE CONCIERTO
ADD CONSTRAINT FK_TIENE_GIRA
FOREIGN KEY (gira_nombre) REFERENCES GIRA(nombre);

-- Relación Vende
CREATE TABLE VENDE (
    concierto_hora TIME,
    concierto_fecha DATE,
    merchandise_id INT,
    PRIMARY KEY (concierto_hora, concierto_fecha, merchandise_id),
    FOREIGN KEY (concierto_hora, concierto_fecha) REFERENCES CONCIERTO(hora_inicio, fecha),
    FOREIGN KEY (merchandise_id) REFERENCES MERCHANDISE(id)
);

-- Tabla Fan
CREATE TABLE FAN (
    rut VARCHAR(20) PRIMARY KEY,
    direccion VARCHAR(200) NOT NULL,
    color_pelo VARCHAR(50),
    helado_favorito VARCHAR(50),
    cuenta_banco VARCHAR(50) NOT NULL,
    direccion_ip VARCHAR(45) NOT NULL
);

-- Relación Compra
CREATE TABLE COMPRA (
    fan_rut VARCHAR(20),
    merchandise_id INT,
    fecha_transaccion DATE NOT NULL,
    PRIMARY KEY (fan_rut, merchandise_id),
    FOREIGN KEY (fan_rut) REFERENCES FAN(rut),
    FOREIGN KEY (merchandise_id) REFERENCES MERCHANDISE(id)
);

-- Relación Asiste
CREATE TABLE ASISTE (
    fan_rut VARCHAR(20),
    concierto_hora TIME,
    concierto_fecha DATE,
    num_ticket VARCHAR(50) NOT NULL,
    PRIMARY KEY (fan_rut, concierto_hora, concierto_fecha),
    FOREIGN KEY (fan_rut) REFERENCES FAN(rut),
    FOREIGN KEY (concierto_hora, concierto_fecha) REFERENCES CONCIERTO(hora_inicio, fecha)
);

-- Tabla Patrocinador
CREATE TABLE PATROCINADOR (
    razon_social VARCHAR(100) PRIMARY KEY,
    tipo_industria VARCHAR(100) NOT NULL,
    cvv VARCHAR(5) NOT NULL
);

-- Relación Auspicia
CREATE TABLE AUSPICIA (
    patrocinador_razon_social VARCHAR(100),
    grupo_nombre VARCHAR(100),
    PRIMARY KEY (patrocinador_razon_social, grupo_nombre),
    FOREIGN KEY (patrocinador_razon_social) REFERENCES PATROCINADOR(razon_social),
    FOREIGN KEY (grupo_nombre) REFERENCES GRUPO(nombre)
);

-- Relación Financia
CREATE TABLE FINANCIA (
    patrocinador_razon_social VARCHAR(100),
    gira_nombre VARCHAR(100),
    PRIMARY KEY (patrocinador_razon_social, gira_nombre),
    FOREIGN KEY (patrocinador_razon_social) REFERENCES PATROCINADOR(razon_social),
    FOREIGN KEY (gira_nombre) REFERENCES GIRA(nombre)
);

-- Insertar datos de ejemplo
-- Tabla GRUPO
INSERT INTO GRUPO (nombre, fecha_debut, listeners_apple, listeners_spotify, nombre_fandom)
VALUES
('BTS', '2013-06-13', 50000000, 60000000, 'ARMY'),
('BLACKPINK', '2016-08-08', 40000000, 55000000, 'BLINK'),
('TWICE', '2015-10-20', 8000000, 11400000, 'ONCE'),
('GFRIEND', '2015-01-16', 1200000, 1900000, 'BUDDY'),
('RED VELVET', '2016-08-08', 3500000, 4900000, 'BLINK'),
('EXO', '2012-04-09', 2500000, 4000000, 'EXO-L'),
('MONSTA X', '2021-05-14', 1000000, 1200000, 'MONBEBE'),
('SEVENTEEN', '2015-05-26', 4000000, 5500000, 'CARAT'),
('NCT2', '2023-04-09', 2000000, 25000000, 'NCTZEN'),
('MAMAMOO', '2014-06-18', 1500000, 1900000, 'MOOMOO');


-- Tabla ALBUM
INSERT INTO ALBUM (nombre, fecha_lanzamiento, concepto, duracion_total, tipo)
VALUES
('Map of the Soul: 7', '2020-02-21', 'Reflexión personal', 74, 'LP'),
('Born Pink', '2022-09-16', 'Empoderamiento femenino', 45, 'EP'),
('Love Yourself: Tear', '2018-05-18', 'Amor y desamor', 42, 'LP'),
('The Album', '2020-10-02', 'Empoderamiento', 24, 'EP'),
('Twicetagram', '2017-10-30', 'Happy', 44, 'LP'),
('Fear', '2015-01-09', 'Amor y desamor', 44, 'LP'),
('Good', '2021-03-03', 'Badass', 27, 'EP'),
('Bad', '2023-12-30', 'Badass', 43, 'LP'),
('Sad', '2020-01-30', 'Amor y desamor', 22, 'EP'),
('Happy', '2018-08-03', 'Happy', 38, 'LP');


-- Tabla CANCION
INSERT INTO CANCION (titulo, genero, reproducciones_apple, reproducciones_spotify)
VALUES
('Dynamite', 'Pop', 1200000000, 1500000000),
('How You Like That', 'Hip-Hop', 800000000, 1000000000),
('Likey', 'K-pop', 200000000, 300000000),
('Bad Boy', 'K-pop', 100000000, 150000000),
('Me gustas tu', 'K-pop', 40000000, 60000000),
('Hip', 'Pop', 80000000, 100000000),
('Without You', 'K-pop', 55000000, 60000000),
('YESTODAY', 'Hip-Hop', 30000000, 40000000),
('VERY NICE', 'Dance', 90000000, 102000000),
('Monster', 'Dance', 66000000, 99000000),
('Butter', 'Pop', 1100000000, 1400000000),
('Kill This Love', 'Hip-Hop', 900000000, 1200000000),
('TT', 'K-pop', 250000000, 350000000),
('Psycho', 'K-pop', 150000000, 200000000),
('Rough', 'K-pop', 50000000, 70000000),
('Egotistic', 'Pop', 85000000, 95000000),
('Cherry Bomb', 'K-pop', 60000000, 70000000),
('Boss', 'Hip-Hop', 40000000, 50000000),
('Clap', 'Dance', 95000000, 105000000),
('Peek-A-Boo', 'Dance', 70000000, 100000000),
('Love Me Harder', 'Pop', 1200000000, 1500000000),
('Ice Cream', 'Hip-Hop', 800000000, 1000000000),
('Fancy', 'K-pop', 200000000, 300000000),
('Feel Special', 'K-pop', 100000000, 150000000),
('Gimme Gimme', 'K-pop', 40000000, 60000000),
('Dalla Dalla', 'Pop', 80000000, 100000000),
('Dynamite (Remix)', 'Hip-Hop', 55000000, 60000000),
('Lovesick', 'K-pop', 30000000, 40000000),
('Love Again', 'Dance', 90000000, 102000000);

-- Tabla CONTIENE
INSERT INTO CONTIENE (album_nombre, album_fecha_lanzamiento, cancion_id)
VALUES
('Map of the Soul: 7', '2020-02-21', 1),
('The Album', '2020-10-02', 2),
('Born Pink', '2022-09-16', 3),
('Love Yourself: Tear', '2018-05-18', 4),
('Twicetagram', '2017-10-30', 5),
('Fear', '2015-01-09', 6),
('Good', '2021-03-03', 7),
('Bad', '2023-12-30', 8),
('Sad', '2020-01-30', 9),
('Happy', '2018-08-03', 10);

-- Tabla ZODIACO
INSERT INTO ZODIACO (fecha_nacimiento, edad, signo_zodiacal)
VALUES
('2004-09-12', 19, 'Virgo'),
('2004-01-16', 19, 'Capricornio'),
('2003-09-22', 20, 'Piscis'),
('2003-12-29', 20, 'Capricornio'),
('2003-04-17', 20, 'Aries'),
('2002-02-21', 21, 'Piscis'),
('2002-05-06', 21, 'Tauro'),
('2003-08-19', 20, 'Leo'),
('2004-05-28', 19, 'Géminis'),
('2003-09-17', 20, 'Virgo'),
('2004-03-27', 19, 'Aries'),
('2004-02-12', 19, 'Acuario'),
('2003-09-03', 20, 'Virgo'),
('2002-03-29', 21, 'Aries'),
('2002-02-10', 21, 'Acuario'),
('2003-01-03', 20, 'Capricornio'),
('2003-08-09', 20, 'Leo'),
('2003-10-06', 20, 'Libra'),
('2002-11-03', 21, 'Escorpio'),
('2003-08-08', 20, 'Leo'),
('2004-12-30', 19, 'Capricornio'),
('2003-06-15', 20, 'Géminis'),
('2003-11-22', 20, 'Piscis'),
('2004-02-11', 19, 'Acuario'),
('2002-11-22', 21, 'Escorpio'),
('2002-01-15', 21, 'Capricornio'),
('2004-03-09', 19, 'Piscis'),
('2004-03-05', 19, 'Piscis'),
('2002-12-04', 21, 'Sagitario'),
('2002-03-09', 21, 'Piscis'),
('2004-09-01', 19, 'Virgo'),
('2003-11-09', 20, 'Escorpio'),
('2004-06-14', 19, 'Géminis'),
('2004-12-09', 19, 'Sagitario');

-- Tabla INTEGRANTE
INSERT INTO INTEGRANTE (rut, nombre, fecha_nacimiento, animal_espiritual)
VALUES
('12345678-9', 'Kim Namjoon', '2004-09-12', 'Koala'),
('98765432-1', 'Jennie Kim', '2004-01-16', 'Pantera'),
('23456789-0', 'Nayeon Im', '2003-09-22', 'Gato'),
('34567890-1', 'Sana Minatozaki', '2003-12-29', 'Delfín'),
('45678901-2', 'Wheein Jung', '2003-04-17', 'León'),
('56789012-3', 'Solar Kim', '2002-02-21', 'Fénix'),
('67890123-4', 'Baekhyun Byun', '2002-05-06', 'Perro'),
('78901234-5', 'Yerin Jung', '2003-08-19', 'Mariposa'),
('89012345-6', 'Dahyun Kim', '2004-05-28', 'Tigre'),
('90123456-7', 'Jin Young', '2003-09-17', 'Búho'),
('01234567-8', 'Lisa Manoban', '2004-03-27', 'Pantera'),
('12345679-0', 'Rosé Park', '2004-02-12', 'Mariposa'),
('23456780-1', 'Joy Park', '2003-09-03', 'Panda'),
('34567891-2', 'Irene Bae', '2002-03-29', 'Gato'),
('45678902-3', 'Seulgi Kang', '2002-02-10', 'Lobo'),
('56789013-4', 'Jisoo Kim', '2003-01-03', 'Gato'),
('67890124-5', 'Chungha Kim', '2003-08-09', 'Mariposa'),
('78901235-6', 'Jooheon Lee', '2003-10-06', 'Tigre'),
('89012346-7', 'Minhyuk Lee', '2002-11-03', 'Perro'),
('90123457-8', 'S.Coups', '2003-08-08', 'León'),
('01234568-9', 'Joshua Hong', '2004-12-30', 'Águila'),
('12345680-0', 'Hoshi Kwon', '2003-06-15', 'Gato'),
('23456781-1', 'Woozi Lee', '2003-11-22', 'Búho'),
('34567892-2', 'Dino Lee', '2004-02-11', 'Mariposa'),
('45678903-3', 'Kihyun Yoo', '2002-11-22', 'Perro'),
('56789014-4', 'Hyunwoo Lee', '2002-01-15', 'Tigre'),
('67890125-5', 'Soojin Seo', '2004-03-09', 'Conejo'),
('78901236-6', 'Yeri Kim', '2004-03-05', 'Mariposa'),
('89012347-7', 'Jin Seokjin', '2002-12-04', 'Perro'),
('90123458-8', 'Suga Min', '2002-03-09', 'Búho'),
('01234569-9', 'Jungkook Jeon', '2004-09-01', 'Águila'),
('12345681-0', 'Momo Hirai', '2003-11-09', 'Delfín'),
('23456782-1', 'Tzuyu Chou', '2004-06-14', 'Gato'),
('34567893-2', 'Yuna Shin', '2004-12-09', 'Mariposa');

-- Tabla RAPERO
INSERT INTO RAPERO (integrante_rut, rapidez)
VALUES
('12345678-9', 95),
('98765432-1', 88),
('23456789-0', 92),
('34567890-1', 85),
('45678901-2', 90),
('56789012-3', 87),
('67890123-4', 93),
('78901234-5', 89),
('89012345-6', 91),
('90123456-7', 94);

-- Tabla CANTANTE
INSERT INTO CANTANTE (integrante_rut, talento)
VALUES
('01234567-8', 90),
('12345679-0', 95),
('23456780-1', 88),
('34567891-2', 92),
('45678902-3', 91),
('56789013-4', 89),
('67890124-5', 93),
('78901235-6', 87),
('89012346-7', 94),
('90123457-8', 96);

-- Tabla BAILARIN
INSERT INTO BAILARIN (integrante_rut, talla_zapatillas)
VALUES
('01234568-9', 42),
('12345680-0', 39),
('23456781-1', 40),
('34567892-2', 41),
('45678903-3', 43),
('56789014-4', 38),
('67890125-5', 41),
('78901236-6', 40),
('89012347-7', 39),
('90123458-8', 42);

-- Tabla PERTENECE_A
INSERT INTO PERTENECE_A (integrante_rut, grupo_nombre)
VALUES
('12345678-9', 'BTS'),
('98765432-1', 'BLACKPINK'),
('23456789-0', 'TWICE'),
('34567890-1', 'GFRIEND'),
('45678901-2', 'RED VELVET'),
('56789012-3', 'EXO'),
('67890123-4', 'MONSTA X'),
('78901234-5', 'SEVENTEEN'),
('89012345-6', 'NCT2'),
('90123456-7', 'MAMAMOO'),
('01234567-8', 'BTS'),
('12345679-0', 'BLACKPINK'),
('23456780-1', 'TWICE'),
('34567891-2', 'GFRIEND'),
('45678902-3', 'RED VELVET'),
('56789013-4', 'EXO'),
('67890124-5', 'MONSTA X'),
('78901235-6', 'SEVENTEEN'),
('89012346-7', 'NCT2'),
('90123457-8', 'MAMAMOO'),
('01234568-9', 'BTS'),
('12345680-0', 'BLACKPINK'),
('23456781-1', 'TWICE'),
('34567892-2', 'GFRIEND'),
('45678903-3', 'RED VELVET'),
('56789014-4', 'EXO'),
('67890125-5', 'MONSTA X'),
('78901236-6', 'SEVENTEEN'),
('89012347-7', 'NCT2'),
('90123458-8', 'MAMAMOO'),
('01234569-9', 'BTS'),
('12345681-0', 'BLACKPINK'),
('23456782-1', 'TWICE'),
('34567893-2', 'GFRIEND');

-- Tabla COMPONE_GRUPO
INSERT INTO COMPONE_GRUPO (grupo_nombre, cancion_id)
VALUES
('BTS', 1),
('BLACKPINK', 2),
('TWICE', 3),
('GFRIEND', 4),
('RED VELVET', 5),
('EXO', 6),
('MONSTA X', 7),
('SEVENTEEN', 8),
('NCT2', 9),
('MAMAMOO', 10);

-- Tabla COMPONE_INTEGRANTE
INSERT INTO COMPONE_INTEGRANTE (integrante_rut, cancion_id)
VALUES
('12345678-9', 11),
('98765432-1', 12),
('23456789-0', 13),
('34567890-1', 14),
('45678901-2', 15),
('56789012-3', 16),
('67890123-4', 17),
('78901234-5', 18),
('89012345-6', 19),
('90123456-7', 20),
('90123456-7', 21),
('90123456-7', 22),
('90123456-7', 23),
('90123456-7', 24),
('90123456-7', 25),
('90123456-7', 26),
('90123456-7', 27),
('90123456-7', 28),
('90123456-7', 29);

-- Tabla COLABORACION
INSERT INTO COLABORACION (grupo_principal_nombre, grupo_feat_nombre, fecha)
VALUES
('BTS', 'BLACKPINK', '2023-01-01'),
('TWICE', 'GFRIEND', '2022-05-15'),
('EXO', 'SEVENTEEN', '2021-11-20'),
('MONSTA X', 'NCT2', '2022-03-10'),
('MAMAMOO', 'RED VELVET', '2022-08-25'),
('BTS', 'MAMAMOO', '2023-06-30'),
('BLACKPINK', 'TWICE', '2023-07-15'),
('GFRIEND', 'EXO', '2023-02-14'),
('SEVENTEEN', 'MONSTA X', '2023-09-01'),
('NCT2', 'BTS', '2023-10-10');

-- Tabla GIRA
INSERT INTO GIRA (nombre, anho_realizacion)
VALUES
('Love Yourself Tour', 2018),
('Born Pink World Tour', 2022),
('TWICE World Tour 3', 2019),
('GFRIEND 2020 Season of GFRIEND', 2020),
('Red Velvet 2021 Concert', 2021),
('EXO Planet #5', 2015),
('MONSTA X World Tour', 2020),
('SEVENTEEN World Tour', 2022),
('NCT World Tour', 2019),
('MAMAMOO 2021 Concert', 2021);

-- Tabla CUENTA_CON
INSERT INTO CUENTA_CON (gira_nombre, cancion_id)
VALUES
('Love Yourself Tour', 1),
('Born Pink World Tour', 2),
('TWICE World Tour 3', 3),
('GFRIEND 2020 Season of GFRIEND', 4),
('Red Velvet 2021 Concert', 5),
('EXO Planet #5', 6),
('MONSTA X World Tour', 7),
('SEVENTEEN World Tour', 8),
('NCT World Tour', 9),
('MAMAMOO 2021 Concert', 10);

-- Tabla REALIZA
INSERT INTO REALIZA (gira_nombre, grupo_nombre)
VALUES
('Love Yourself Tour', 'BTS'),
('Born Pink World Tour', 'BLACKPINK'),
('TWICE World Tour 3', 'TWICE'),
('GFRIEND 2020 Season of GFRIEND', 'GFRIEND'),
('Red Velvet 2021 Concert', 'RED VELVET'),
('EXO Planet #5', 'EXO'),
('MONSTA X World Tour', 'MONSTA X'),
('SEVENTEEN World Tour', 'SEVENTEEN'),
('NCT World Tour', 'NCT2'),
('MAMAMOO 2021 Concert', 'MAMAMOO');

-- Tabla PAIS
INSERT INTO PAIS (nombre, num_ciudades, porcentaje_kpop_listeners)
VALUES
('Corea del Sur', 10, 80.50),
('Estados Unidos', 50, 20.30),
('Japón', 15, 70.00),
('México', 32, 30.00),
('Brasil', 26, 25.00),
('España', 12, 15.00),
('Canadá', 10, 18.00),
('Alemania', 16, 12.00),
('Francia', 11, 14.00),
('Australia', 8, 22.00);

-- Tabla CONCIERTO
INSERT INTO CONCIERTO (hora_inicio, fecha, num_participantes, bool_sold_out, gira_nombre)
VALUES
('19:00:00', '2018-09-09', 50000, TRUE, 'Love Yourself Tour'),
('20:00:00', '2022-11-12', 60000, TRUE, 'Born Pink World Tour'),
('18:30:00', '2019-05-30', 45000, TRUE, 'TWICE World Tour 3'),
('19:30:00', '2020-07-10', 40000, FALSE, 'GFRIEND 2020 Season of GFRIEND'),
('20:00:00', '2021-06-15', 55000, TRUE, 'Red Velvet 2021 Concert'),
('21:00:00', '2015-07-20', 70000, TRUE, 'EXO Planet #5'),
('19:00:00', '2020-08-15', 60000, FALSE, 'MONSTA X World Tour'),
('20:00:00', '2022-05-10', 50000, FALSE, 'SEVENTEEN World Tour'),
('21:00:00', '2019-09-25', 30000, TRUE, 'NCT World Tour'),
('20:30:00', '2021-04-20', 65000, TRUE, 'MAMAMOO 2021 Concert');

-- Tabla POR
INSERT INTO POR (gira_nombre, pais_nombre)
VALUES
('Love Yourself Tour', 'Corea del Sur'),
('Born Pink World Tour', 'Estados Unidos'),
('TWICE World Tour 3', 'Japón'),
('GFRIEND 2020 Season of GFRIEND', 'Corea del Sur'),
('Red Velvet 2021 Concert', 'México'),
('EXO Planet #5', 'España'),
('MONSTA X World Tour', 'Canadá'),
('SEVENTEEN World Tour', 'Australia'),
('NCT World Tour', 'Francia'),
('MAMAMOO 2021 Concert', 'Alemania');

-- Tabla MERCHANDISE
INSERT INTO MERCHANDISE (producto, precio, bool_exclusividad)
VALUES
('Lightstick BTS', 50.00, TRUE),
('Lightstick BLACKPINK', 45.00, TRUE),
('Lightstick TWICE', 25.00, TRUE),
('Lightstick GFRIEND', 35.00, TRUE),
('Lightstick Red Velvet', 40.00, TRUE),
('Lightstick EXO', 55.00, TRUE),
('Lightstick MONSTA X', 45.00, TRUE),
('Lightstick SEVENTEEN', 50.00, TRUE),
('Lightstick NCT2', 48.00, TRUE),
('Lightstick MAMAMOO', 42.00, TRUE);

-- Tabla VENDE
INSERT INTO VENDE (concierto_hora, concierto_fecha, merchandise_id)
VALUES
('19:00:00', '2018-09-09', 1),
('20:00:00', '2022-11-12', 2),
('18:30:00', '2019-05-30', 3),
('19:30:00', '2020-07-10', 4),
('20:00:00', '2021-06-15', 5),
('21:00:00', '2015-07-20', 6),
('19:00:00', '2020-08-15', 7),
('20:00:00', '2022-05-10', 8),
('21:00:00', '2019-09-25', 9),
('20:30:00', '2021-04-20', 10);

-- Tabla FAN
INSERT INTO FAN (rut, direccion, color_pelo, helado_favorito, cuenta_banco, direccion_ip)
VALUES
('11111111-1', 'Seúl, Corea del Sur', 'Negro', 'Vainilla', '123456789', '192.168.1.1'),
('22222222-2', 'Los Ángeles, EE.UU.', 'Rubio', 'Chocolate', '987654321', '192.168.1.2'),
('33333333-3', 'Tokio, Japón', 'Castaño', 'Fresa', '456789123', '192.168.1.3'),
('44444444-4', 'Ciudad de México, México', 'Negro', 'Menta', '321654987', '192.168.1.4'),
('55555555-5', 'Londres, Reino Unido', 'Rubio', 'Nuez', '654321789', '192.168.1.5'),
('66666666-6', 'Toronto, Canadá', 'Castaño', 'Café', '789123456', '192.168.1.6'),
('77777777-7', 'Berlín, Alemania', 'Negro', 'Vainilla', '159753486', '192.168.1.7'),
('88888888-8', 'París, Francia', 'Rubio', 'Chocolate', '753159486', '192.168.1.8'),
('99999999-9', 'Sídney, Australia', 'Castaño', 'Fresa', '951753486', '192.168.1.9'),
('00000000-0', 'Seúl, Corea del Sur', 'Negro', 'Menta', '852963741', '192.168.1.10');

-- Tabla COMPRA
INSERT INTO COMPRA (fan_rut, merchandise_id, fecha_transaccion)
VALUES
('11111111-1', 1, '2018-09-10'),
('22222222-2', 2, '2022-11-13'),
('33333333-3', 3, '2019-06-01'),
('44444444-4', 4, '2020-07-15'),
('55555555-5', 5, '2021-06-20'),
('66666666-6', 6, '2015-07-20'),
('77777777-7', 7, '2020-09-10'),
('88888888-8', 8, '2022-05-15'),
('99999999-9', 9, '2019-09-30'),
('00000000-0', 10, '2021-04-25');

-- Tabla ASISTE
INSERT INTO ASISTE (fan_rut, concierto_hora, concierto_fecha, num_ticket)
VALUES
('11111111-1', '19:00:00', '2018-09-09', 'A123'),
('22222222-2', '20:00:00', '2022-11-12', 'B456'),
('33333333-3', '18:30:00', '2019-05-30', 'C789'),
('44444444-4', '19:30:00', '2020-07-10', 'D012'),
('55555555-5', '20:00:00', '2021-06-15', 'E345'),
('66666666-6', '21:00:00', '2015-07-20', 'F678'),
('77777777-7', '19:00:00', '2020-08-15', 'G901'),
('88888888-8', '20:00:00', '2022-05-10', 'H234'),
('99999999-9', '21:00:00', '2019-09-25', 'I567'),
('00000000-0', '20:30:00', '2021-04-20', 'J890');

-- Tabla PATROCINADOR
INSERT INTO PATROCINADOR (razon_social, tipo_industria, cvv)
VALUES
('Samsung', 'Tecnología', '123'),
('Pepsi', 'Bebidas', '456'),
('Coca-Cola', 'Bebidas', '789'),
('LG', 'Electrodomésticos', '321'),
('Hyundai', 'Automotriz', '654'),
('Kia', 'Automotriz', '987'),
('Naver', 'Tecnología', '159'),
('Daum', 'Tecnología', '753'),
('SK Telecom', 'Telecomunicaciones', '852'),
('CJ ENM', 'Entretenimiento', '369');

-- Tabla AUSPICIA
INSERT INTO AUSPICIA (patrocinador_razon_social, grupo_nombre)
VALUES
('Samsung', 'BTS'),
('Pepsi', 'BLACKPINK'),
('Coca-Cola', 'TWICE'),
('LG', 'GFRIEND'),
('Hyundai', 'RED VELVET'),
('Kia', 'EXO'),
('Naver', 'MONSTA X'),
('Daum', 'SEVENTEEN'),
('SK Telecom', 'NCT2'),
('CJ ENM', 'MAMAMOO');

-- Tabla FINANCIA
INSERT INTO FINANCIA (patrocinador_razon_social, gira_nombre)
VALUES
('Samsung', 'Love Yourself Tour'),
('Pepsi', 'Born Pink World Tour'),
('Coca-Cola', 'TWICE World Tour 3'),
('LG', 'GFRIEND 2020 Season of GFRIEND'),
('Hyundai', 'Red Velvet 2021 Concert'),
('Kia', 'EXO Planet #5'),
('Naver', 'MONSTA X World Tour'),
('Daum', 'SEVENTEEN World Tour'),
('SK Telecom', 'NCT World Tour'),
('CJ ENM', 'MAMAMOO 2021 Concert');