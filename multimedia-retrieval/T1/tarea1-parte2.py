# CC5213 - TAREA 1 - RECUPERACIÓN DE INFORMACIÓN MULTIMEDIA
# Fecha: 27 de marzo de 2025
# Alumno: Adolfo Rojas V.

import numpy
import cv2
import scipy.spatial
import sys
import os
import util as util

# ---- Funciones descriptoras ----
def descriptor_intensidades(nombre_imagen, imagenes_dir):
    archivo_imagen = imagenes_dir + "/" + nombre_imagen
    imagen = cv2.imread(archivo_imagen, cv2.IMREAD_GRAYSCALE)
    if imagen is None:
        raise Exception("no puedo abrir: " + archivo_imagen)
    # se puede cambiar a otra interpolacion, como cv2.INTER_CUBIC
    imagen_reducida = cv2.resize(imagen, (5, 5), interpolation=cv2.INTER_AREA)
    # flatten convierte una matriz de nxm en un array de largo nxm
    descriptor_imagen = imagen_reducida.flatten()
    return descriptor_imagen

def descriptor_intensidades_flip(nombre_imagen, imagenes_dir):
    archivo_imagen = imagenes_dir + "/" + nombre_imagen
    imagen = cv2.imread(archivo_imagen, cv2.IMREAD_GRAYSCALE)
    if imagen is None:
        raise Exception("no puedo abrir: " + archivo_imagen)
    imagen_flip = cv2.flip(imagen, 1)
    imagen_reducida = cv2.resize(imagen_flip, (5, 5), interpolation=cv2.INTER_AREA)
    return imagen_reducida.flatten()

def descriptor_equalizeHist(nombre_imagen, imagenes_dir):
    archivo_imagen = imagenes_dir + "/" + nombre_imagen
    imagen = cv2.imread(archivo_imagen, cv2.IMREAD_GRAYSCALE)
    if imagen is None:
        raise Exception("no puedo abrir: " + archivo_imagen)
    # ecualizacion
    imagen = cv2.equalizeHist(imagen)
    # se puede cambiar a otra interpolacion, como cv2.INTER_CUBIC
    imagen_reducida = cv2.resize(imagen, (5, 5), interpolation=cv2.INTER_AREA)
    # flatten convierte una matriz de nxm en un array de largo nxm
    descriptor_imagen = imagen_reducida.flatten()
    return descriptor_imagen
# ---- Fin de funciones descriptoras ----

# Calcula los descriptores de las imágenes en nombres_imagenes
def calcular_descriptores(funcion_descriptor, nombres_imagenes, imagenes_dir):
    matriz_descriptores = None
    num_fila = 0
    # t0 = time.time()
    for nombre_imagen in nombres_imagenes:
        descriptor_imagen = funcion_descriptor(nombre_imagen, imagenes_dir)
        # crear la matriz de descriptores (numero imagenes x largo_descriptor)
        if matriz_descriptores is None:
            matriz_descriptores = numpy.zeros((len(nombres_imagenes), len(descriptor_imagen)), numpy.float32)
        # copiar descriptor a una fila de la matriz de descriptores
        matriz_descriptores[num_fila] = descriptor_imagen
        num_fila += 1
    # print("tiempo calcular descriptores = {:.1f} segundos".format(time.time() - t0))
    return matriz_descriptores

# --- Comienzo de la tarea 1.2 ---
def tarea1_parte2(dir_input_imagenes_Q, dir_input_descriptores_R, file_output_resultados):
    if not os.path.isdir(dir_input_imagenes_Q):
        print("ERROR: no existe directorio {}".format(dir_input_imagenes_Q))
        sys.exit(1)
    elif not os.path.isdir(dir_input_descriptores_R):
        print("ERROR: no existe directorio {} (¿terminó bien tarea1-parte1.py?)".format(dir_input_descriptores_R))
        sys.exit(1)
    elif os.path.exists(file_output_resultados):
        print("ERROR: ya existe archivo {}".format(file_output_resultados))
        sys.exit(1)
    # Implementar la fase online

    # 1-calcular descriptores de Q para imágenes en dir_input_imagenes_Q
    imagenes_Q = util.listar_archivos_con_extension(dir_input_imagenes_Q, ".jpg")

    # 2-leer descriptores de R guardados en dir_input_descriptores_R
    # puede servir la funcion util.leer_objeto() que está definida en util.py
    descriptores_R = util.leer_objeto(dir_input_descriptores_R, "descriptores.pkl")
    # 3-para cada descriptor q localizar el mas cercano en R (utilizando la distancia de Manhattan)
    imagenes_Q = util.listar_archivos_con_extension(dir_input_imagenes_Q, ".jpg")
    tabla_resultados = []
    for descriptor in [descriptor_intensidades, descriptor_intensidades_flip, descriptor_equalizeHist]:
        descriptores_Q = calcular_descriptores(descriptor, imagenes_Q, dir_input_imagenes_Q)
        for i in range(len(imagenes_Q)):
            imagen_q = imagenes_Q[i]
            descriptor_q = descriptores_Q[i]
            # calcular distancia de manhattan entre descriptor_q y cada descriptor_r
            matriz_distancias = scipy.spatial.distance.cdist([descriptor_q], descriptores_R, metric='cityblock')[0]

            posiciones_minimas = numpy.argmin(matriz_distancias)
            distancia = matriz_distancias[posiciones_minimas]
            imagen_r = f"r{posiciones_minimas+1:04d}.jpg"
            tabla_resultados.append([imagen_q, imagen_r, distancia])
    
    # 4-escribir en el archivo file_output_resultados un archivo con tres columnas separado por \t:
    # columna 1: imagen_q
    # columna 2: imagen_r
    # columna 3: distancia
    # Puede servir la funcion util.escribir_lista_de_columnas_en_archivo() que está definida util.py
    util.escribir_lista_de_columnas_en_archivo(tabla_resultados, file_output_resultados)

# inicio de la tarea
if len(sys.argv) < 4:
    print("Uso: {}  dir_input_imagenes_Q  dir_input_descriptores_R  file_output_resultados".format(sys.argv[0]))
    sys.exit(1)

# lee los parametros de entrada
dir_input_imagenes_Q = sys.argv[1]
dir_input_descriptores_R = sys.argv[2]
file_output_resultados = sys.argv[3]

# ejecuta la tarea
tarea1_parte2(dir_input_imagenes_Q, dir_input_descriptores_R, file_output_resultados)