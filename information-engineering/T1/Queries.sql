USE YiWaiPi;

-- Obtener todos los grupos y sus características.
SELECT *
FROM grupo;

-- Obtener el país que tiene más porcentaje de población que escucha K-pop.
SELECT *
FROM pais
ORDER BY porcentaje_kpop_listeners DESC
LIMIT 1;

-- Obtener el animal espiritual que más se repite en los integrantes.
SELECT animal_espiritual
FROM integrante
GROUP BY animal_espiritual
ORDER BY COUNT(*) DESC
LIMIT 1;

-- Crear una consulta para obtener la razón social y el CVV de los patrocinadores dependiendo de la industria a la que pertenecen.
SELECT razon_social, cvv
FROM patrocinador
WHERE tipo_industria = 'Tecnología';  -- Puede cambiarse para considerar otras industrias.

-- Porcentaje de conciertos que fueron SoldOut.
SELECT COUNT(*) * 100.0 / (SELECT COUNT(*) FROM concierto) AS porcentaje_soldout
FROM concierto
WHERE bool_sold_out = 1;

-- Crear una consulta para obtener la duración y el nombre del álbum dependiendo del concepto de este último.
SELECT duracion_total, nombre
FROM album
WHERE concepto = 'Amor y desamor'; -- Puede cambiarse para considerar los demás conceptos.

-- Crear una consulta para obtener todas las giras que se han realizado en España con un fandom menor al 90% dependiendo del grupo.
SELECT g.nombre, g.anho_realizacion
FROM gira g
JOIN por ON g.nombre = por.gira_nombre
JOIN pais p ON por.pais_nombre = p.nombre
WHERE p.nombre = 'España' AND p.porcentaje_kpop_listeners < 90;

-- Obtener todos los grupos que han colaborado entre sí y que su debut haya sido después del año 2020.
SELECT g1.nombre AS g_1, g2.nombre AS g_2
FROM colaboracion c
JOIN grupo g1 ON c.grupo_principal_nombre = g1.nombre
JOIN grupo g2 ON c.grupo_feat_nombre = g2.nombre
WHERE g1.fecha_debut > '2020-01-01' AND g2.fecha_debut > '2020-01-01';

-- Obtener el RUT del integrante que tiene canciones como solista con más reproducciones en Spotify.
SELECT i.rut
FROM integrante i
JOIN compone_integrante ci ON i.rut = ci.integrante_rut
JOIN cancion c ON ci.cancion_id = c.id
GROUP BY i.rut
ORDER BY SUM(c.reproducciones_spotify) DESC
LIMIT 1;

-- Obtener el producto más caro del merchandizing de alguna gira que se haya realizado el año 2015.
SELECT m.producto, m.precio
FROM gira g
JOIN concierto c ON g.nombre = c.gira_nombre
JOIN vende v ON c.hora_inicio = v.concierto_hora AND c.fecha = v.concierto_fecha
JOIN merchandise m ON v.merchandise_id = m.id
WHERE g.anho_realizacion = 2015
ORDER BY m.precio DESC
LIMIT 1;

-- Obtener el nombre del álbum que tenga una duración total mayor a 40 min en donde un integrante sea rapero, su signo zodiacal sea piscis y que tenga 20 años.
SELECT a.nombre
FROM album a
JOIN contiene con ON a.nombre = con.album_nombre AND a.fecha_lanzamiento = con.album_fecha_lanzamiento
JOIN cancion c ON con.cancion_id = c.id
JOIN compone_grupo cg ON c.id = cg.cancion_id
JOIN pertenece_a pa ON cg.grupo_nombre = pa.grupo_nombre
JOIN integrante i ON pa.integrante_rut = i.rut
JOIN rapero ON rapero.integrante_rut = i.rut
JOIN zodiaco ON zodiaco.fecha_nacimiento = i.fecha_nacimiento
WHERE a.duracion_total > 40 AND zodiaco.edad = 20 AND zodiaco.signo_zodiacal = 'Piscis';

-- Obtener el número de ticket de aquellos fans que su dígito verificador sea 8, que haya ido a un concierto que no fue soldout y que hayan comprado merch exclusivo.
SELECT a.num_ticket
FROM ASISTE a
JOIN FAN f ON a.fan_rut = f.rut
JOIN CONCIERTO c ON a.concierto_hora = c.hora_inicio AND a.concierto_fecha = c.fecha
JOIN COMPRA co ON f.rut = co.fan_rut
JOIN MERCHANDISE m ON co.merchandise_id = m.id
-- RIGHT nos permite obtener el dígito verificador del rut
WHERE RIGHT(f.rut, 1) = '8'
  AND c.bool_sold_out = 0
  AND m.bool_exclusividad = 1;