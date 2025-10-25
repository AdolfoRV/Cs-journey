# CC5213 - TAREA 2 - RECUPERACIÓN DE INFORMACIÓN MULTIMEDIA
# Fecha: 08 de mayo de 2025
# Alumno: Adolfo I. Rojas Valenzuela

import sys
import os
import util as util
import numpy as np
from collections import defaultdict

def tarea2_parte3(carpeta_ventanas_similares, archivo_salida_detecciones_txt):
    if not os.path.isdir(carpeta_ventanas_similares):
        print("ERROR: no existe carpeta {}".format(carpeta_ventanas_similares))
        sys.exit(1)
    elif os.path.exists(archivo_salida_detecciones_txt):
        print("ERROR: ya existe {}".format(archivo_salida_detecciones_txt))
        sys.exit(1)
    #
    # Implementar la tarea con los siguientes pasos:
    #
    #  1-leer el o los archivos en carpeta_ventanas_similares (fue creado por tarea2_parte2)
    #    puede servir la funcion util.leer_objeto() que está definida en util.py
    print("Cargando datos de ventanas similares...")
    ventanas_similares = util.leer_objeto(carpeta_ventanas_similares, "ventanas_similares.pkl")
    print("Total ventanas similares cargadas: {}".format(len(ventanas_similares)))

    #  2-crear un algoritmo para buscar secuencias similares entre audios
    #    ver material de la semanas 5 y 7
    #    identificar grupos de ventanas de Q y R que son similares y pertenecen a las mismas canciones con el mismo desfase
    print("Analizando secuencias con desfase temporal constante...")
    
    # Parámetros del algoritmo (más permisivos para detectar más canciones)
    tiempo_ventana = 4096 / 44100  # ~0.093 segundos por ventana
    umbral_votos = 9  # umbral mínimo de votos para considerar una ventana como similar (más permisivo)
    tolerancia_desfase = 4.1  # tolerancia en segundos para agrupar desfases (más permisivo)
    min_votos = 1  # mínimo número de ventanas similares (más permisivo)
    
    # Agrupar ventanas similares por (archivo_Q, archivo_R)
    grupos_archivos = defaultdict(list)
    
    for similitud in ventanas_similares:
        archivo_Q = os.path.basename(similitud['ventana_query']['nombre_archivo'])
        archivo_R = os.path.basename(similitud['ventana_conocida']['nombre_archivo'])
        tiempo_Q = similitud['ventana_query']['inicio']
        tiempo_R = similitud['ventana_conocida']['inicio']
        votos = similitud['votos']
        
        # Solo considerar ventanas con suficientes votos (en lugar de distancia)
        if votos >= umbral_votos:  # usar el umbral definido
            # Calcular el desfase temporal: tiempo_Q - tiempo_R
            desfase = tiempo_Q - tiempo_R
            
            grupos_archivos[(archivo_Q, archivo_R)].append({
                'tiempo_Q': tiempo_Q,
                'tiempo_R': tiempo_R,
                'desfase': desfase,
                'votos': votos
            })
        
    # Para cada par de archivos, buscar desfases constantes
    detecciones = []
    
    for (archivo_Q, archivo_R), ventanas in grupos_archivos.items():
        if len(ventanas) < min_votos:
            continue
        # Algoritmo de votación por desfase temporal
        # Redondear desfases para agrupar ventanas con desfases similares
        votos_desfase = defaultdict(list)
        
        for ventana in ventanas:
            # Redondear el desfase a intervalos de tolerancia_desfase
            desfase_redondeado = round(ventana['desfase'] / tolerancia_desfase) * tolerancia_desfase
            votos_desfase[desfase_redondeado].append(ventana)
        
        # Evaluar cada grupo de desfase
        for desfase, ventanas_grupo in votos_desfase.items():
            num_votos = len(ventanas_grupo)
            
            if num_votos < min_votos:
                continue
            
            # Verificar que las ventanas forman una secuencia temporal coherente
            # Ordenar por tiempo Q
            ventanas_grupo.sort(key=lambda x: x['tiempo_Q'])
            
            # Calcular estadísticas de la secuencia
            tiempo_inicio_Q = ventanas_grupo[0]['tiempo_Q']
            tiempo_fin_Q = ventanas_grupo[-1]['tiempo_Q']
            duracion_secuencia = tiempo_fin_Q - tiempo_inicio_Q
            
            # Calcular densidad de ventanas (ventanas encontradas / ventanas posibles)
            ventanas_posibles = max(1, int(duracion_secuencia / tiempo_ventana))
            densidad = num_votos / ventanas_posibles
            
            # Calcular votos promedio
            votos_promedio = np.mean([v['votos'] for v in ventanas_grupo])
            
            # Calcular varianza del desfase (debe ser baja para una buena detección)
            desfases_reales = [v['desfase'] for v in ventanas_grupo]
            varianza_desfase = np.var(desfases_reales)
            
            # Fórmula de confianza basada en:
            # - Número de votos (más votos = más confianza)
            # - Densidad de la secuencia (más densa = más confianza)
            # - Duración de la secuencia (más larga = más confianza)
            # - Votos promedio (más votos por ventana = más confianza)
            # - Consistencia del desfase (menor varianza = más confianza)
            confianza = (
                num_votos * 0.5 +                    # peso por número de votos
                densidad * 20 +                      # peso por densidad
                duracion_secuencia * 0.1 +          # peso por duración
                votos_promedio * 0.01 +             # peso por votos promedio
                max(0, 10 - varianza_desfase * 10)         # peso por consistencia desfase
            )
            
            confianza = max(1.0, confianza)  # mínimo confianza de 1.0
            
            # El tiempo de inicio de la detección en Q es cuando comienza la canción R en Q
            # Si tenemos ventanas de Q que matchean con ventanas de R, y sabemos el desfase,
            # entonces el inicio de la canción R en Q es: primera_ventana_Q - primera_ventana_R_correspondiente
            
            # Buscar la ventana más temprana en R en este grupo
            tiempo_R_min = min([v['tiempo_R'] for v in ventanas_grupo])
            
            # Para esa ventana R mínima, encontrar su correspondiente Q
            ventana_R_min = next(v for v in ventanas_grupo if v['tiempo_R'] == tiempo_R_min)
            
            # El tiempo de inicio de la canción en Q es cuando la ventana R=0 aparecería en Q
            tiempo_inicio_deteccion = ventana_R_min['tiempo_Q'] - tiempo_R_min
            
            deteccion = [
                archivo_Q,                              # columna 1: archivo Q
                "{:.1f}".format(tiempo_inicio_deteccion),  # columna 2: tiempo inicio
                archivo_R,                              # columna 3: archivo R  
                "{:.2f}".format(confianza)              # columna 4: confianza
            ]
            
            detecciones.append(deteccion)
    
    #  3-escribir las detecciones encontradas en archivo_salida_detecciones_txt:
    #    columna 1: nombre de archivo Q (nombre de archivo en carpeta radio)
    #    columna 2: tiempo de inicio (número decimal, tiempo en segundos del inicio de la detección)
    #    columna 3: nombre de archivo R (nombre de archivo en carpeta canciones)
    #    columna 4: confianza (número decimal, mientras más alto mayor confianza que la respuesta es correcta)
    #   le puede servir la funcion util.escribir_lista_de_columnas_en_archivo() que está definida util.py
    detecciones.sort(key=lambda x: (x[0], float(x[1])))    
    if len(detecciones) > 0:
        util.escribir_lista_de_columnas_en_archivo(detecciones, archivo_salida_detecciones_txt)
    else:
        with open(archivo_salida_detecciones_txt, 'w') as f:
            pass
    


# inicio de la tarea
if len(sys.argv) != 3:
    print("Uso: {} [carpeta_ventanas_similares] [archivo_salida_detecciones_txt]".format(sys.argv[0]))
    sys.exit(1)

# lee los parametros de entrada
carpeta_ventanas_similares = sys.argv[1]
archivo_salida_detecciones_txt = sys.argv[2]

# llamar a la tarea
tarea2_parte3(carpeta_ventanas_similares, archivo_salida_detecciones_txt)
