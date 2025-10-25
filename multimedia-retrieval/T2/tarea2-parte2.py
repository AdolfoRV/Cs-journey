# CC5213 - TAREA 2 - RECUPERACIÓN DE INFORMACIÓN MULTIMEDIA
# Fecha: 08 de mayo de 2025
# Alumno: [nombre]

import sys
import os
import util as util
import numpy as np
from scipy.spatial.distance import cdist

def crear_voto(name, query, conocido):
    """Crea un nuevo voto usando diccionarios"""
    return {
        'name': name,
        'query_nombre': query['nombre_archivo'],
        'query_inicio': query['segundos_desde'],
        'query_fin': query['segundos_hasta'],
        'conocido_nombre': conocido['nombre_archivo'],
        'conocido_inicio': conocido['segundos_desde'],
        'conocido_fin': conocido['segundos_hasta'],
        'numVotos': 1
    }

def agregar_voto(voto, query, conocido):
    """Agrega un voto a una agrupación existente"""
    # muevo el final de la zona y sumo un voto
    voto['query_fin'] = query['segundos_hasta']
    voto['conocido_fin'] = conocido['segundos_hasta']
    voto['numVotos'] += 1

def voto_a_string(voto):
    """Convierte un voto a string para mostrar"""
    return "{} entre [{:6.3f}-{:6.3f}]  se parece a  {} entre [{:6.3f}-{:6.3f}]  ({} votos)".format(
        os.path.basename(voto['query_nombre']), voto['query_inicio'], voto['query_fin'], 
        os.path.basename(voto['conocido_nombre']), voto['conocido_inicio'], voto['conocido_fin'],
        voto['numVotos'])
    
def tarea2_parte2(carpeta_descriptores_radio_Q, carpeta_descritores_canciones_R, carpeta_salida_ventanas_similares):
    if not os.path.isdir(carpeta_descriptores_radio_Q):
        print("ERROR: no existe carpeta {}".format(carpeta_descriptores_radio_Q))
        sys.exit(1)
    elif not os.path.isdir(carpeta_descritores_canciones_R):
        print("ERROR: no existe carpeta {}".format(carpeta_descritores_canciones_R))
        sys.exit(1)
    elif os.path.exists(carpeta_salida_ventanas_similares):
        print("ERROR: ya existe {}".format(carpeta_salida_ventanas_similares))
        sys.exit(1)
    
    # Crear carpeta de salida
    os.makedirs(carpeta_salida_ventanas_similares, exist_ok=True)
    
    #  1-leer descriptores globales de Q y R
    mfcc_Q = util.leer_objeto(carpeta_descriptores_radio_Q, "descriptor_mfcc_global.pkl")
    ventanas_Q = util.leer_objeto(carpeta_descriptores_radio_Q, "descriptor_ventanas_global.pkl")
    
    mfcc_R = util.leer_objeto(carpeta_descritores_canciones_R, "descriptor_mfcc_global.pkl")
    ventanas_R = util.leer_objeto(carpeta_descritores_canciones_R, "descriptor_ventanas_global.pkl")

    #  2-para cada descriptor de Q localizar el más cercano en R
    matriz_distancias = cdist(mfcc_Q, mfcc_R, metric='cityblock')
    
    # Encontrar el más cercano para cada ventana de Q
    posicion_min = np.argmin(matriz_distancias, axis=1)
    minimo = np.amin(matriz_distancias, axis=1)
    
    # Acumular votos por desfase temporal
    contadores = dict()

    for i in range(len(ventanas_Q)):
        query = ventanas_Q[i]
        conocido = ventanas_R[posicion_min[i]]
        diferencia = round(conocido['segundos_desde'] - query['segundos_desde'], 1)
        
        # llave para acumular (se podría mejorar la acumulación si la diferencia se redondea)
        key = "{}-{}-{:4.1f}".format(
            os.path.basename(query['nombre_archivo']), 
            os.path.basename(conocido['nombre_archivo']), 
            diferencia
        )
        
        # ver si hay votos anteriores
        votos = contadores.get(key)
        if votos is None:
            # se inicia votacion por ese desfase
            votos = crear_voto(key, query, conocido)
            contadores[key] = votos
        else:
            # suma un voto a una deteccion encontrada previamente con el mismo desfase
            agregar_voto(votos, query, conocido)

    # mostrar las mayores votaciones y guardar resultados
    allVotos = list(contadores.values())
    print(f"Total de agrupaciones encontradas: {len(allVotos)}")
    
    ventanas_similares = []
    for v in sorted(allVotos, key=lambda x: x['numVotos'], reverse=True):
        if v['numVotos'] > 10:
            print(voto_a_string(v))
            resultado = {
                "ventana_query": {
                    "nombre_archivo": v['query_nombre'],
                    "inicio": v['query_inicio'],
                    "fin": v['query_fin']
                },
                "ventana_conocida": {
                    "nombre_archivo": v['conocido_nombre'],
                    "inicio": v['conocido_inicio'],
                    "fin": v['conocido_fin']
                },
                "votos": v['numVotos']
            }
            ventanas_similares.append(resultado)

    #  3-guardar resultados
    util.guardar_objeto(ventanas_similares, carpeta_salida_ventanas_similares, "ventanas_similares.pkl")
    print(f"Se guardaron {len(ventanas_similares)} detecciones en ventanas_similares.pkl")

# inicio de la tarea
if len(sys.argv) != 4:
    print(
        "Uso: {} [carpeta_descriptores_radio_Q] [carpeta_descritores_canciones_R] [carpeta_salida_ventanas_similares]".format(
            sys.argv[0]))
    sys.exit(1)

# lee los parametros de entrada
carpeta_descriptores_radio_Q = sys.argv[1]
carpeta_descritores_canciones_R = sys.argv[2]
carpeta_salida_ventanas_similares = sys.argv[3]

# llamar a la tarea
tarea2_parte2(carpeta_descriptores_radio_Q, carpeta_descritores_canciones_R, carpeta_salida_ventanas_similares)
