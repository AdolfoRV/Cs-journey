# CC5213 - TAREA 1 - RECUPERACIÓN DE INFORMACIÓN MULTIMEDIA
# Fecha: 27 de marzo de 2025
# Alumno: Adolfo Rojas V.

import numpy
import cv2
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

def descriptor_intensidades_omd(nombre_imagen, imagenes_dir):
    archivo_imagen = imagenes_dir + "/" + nombre_imagen
    imagen = cv2.imread(archivo_imagen, cv2.IMREAD_GRAYSCALE)
    if imagen is None:
        raise Exception("no puedo abrir: " + archivo_imagen)
    # se puede cambiar a otra interpolacion, como cv2.INTER_CUBIC
    imagen_reducida = cv2.resize(imagen, (5, 5), interpolation=cv2.INTER_AREA)
    # flatten convierte una matriz de nxm en un array de largo nxm
    descriptor_imagen = imagen_reducida.flatten()
    # la posicion si se ordenan
    posiciones = numpy.argsort(descriptor_imagen)
    # reemplazar el valor gris por su  posicion
    for i in range(len(posiciones)):
        descriptor_imagen[posiciones[i]] = i
    return descriptor_imagen

def descriptor_histogramas_por_zonas(nombre_imagen, imagenes_dir):
    archivo_imagen = imagenes_dir + "/" + nombre_imagen
    imagen = cv2.imread(archivo_imagen, cv2.IMREAD_GRAYSCALE)
    if imagen is None:
        raise Exception("no puedo abrir: " + archivo_imagen)
    imagen = cv2.equalizeHist(imagen)

    # función auxiliar para calcular el histograma por zonas
    def histograma_por_zona(imagen):
        num_zonas_x = 1
        num_zonas_y = 8
        num_bins_por_zona = 16
        descriptor = []
        for j in range(num_zonas_y):
            desde_y = int(imagen.shape[0] / num_zonas_y * j)
            hasta_y = int(imagen.shape[0] / num_zonas_y * (j + 1))
            for i in range(num_zonas_x):
                desde_x = int(imagen.shape[1] / num_zonas_x * i)
                hasta_x = int(imagen.shape[1] / num_zonas_x * (i + 1))
                zona = imagen[desde_y:hasta_y, desde_x:hasta_x]
                histograma, _ = numpy.histogram(zona, bins=num_bins_por_zona, range=(0, 255))
                histograma = histograma / numpy.sum(histograma)
                descriptor.extend(histograma)
        return numpy.array(descriptor, dtype=numpy.float32)
    
    return histograma_por_zona(imagen)

def descriptor_HOG(nombre_imagen, imagenes_dir):
    archivo_imagen = imagenes_dir + "/" + nombre_imagen
    imagen = cv2.imread(archivo_imagen, cv2.IMREAD_GRAYSCALE)
    if imagen is None:
        raise Exception("no puedo abrir: " + archivo_imagen)
    imagen2 = cv2.GaussianBlur(imagen, (5, 5), 0, 0)
    sobelX = cv2.Sobel(imagen2, ddepth=cv2.CV_32F, dx=1, dy=0, ksize=3)
    sobelY = cv2.Sobel(imagen2, ddepth=cv2.CV_32F, dx=0, dy=1, ksize=3)
    magnitud = numpy.sqrt(numpy.square(sobelX) + numpy.square(sobelY))
    threshold_magnitud_gradiente = 100
    _, imagen_bordes = cv2.threshold(magnitud, threshold_magnitud_gradiente, 255, cv2.THRESH_BINARY)
    mascara = imagen_bordes == 255
    angulos = numpy.arctan2(sobelY, sobelX, where=mascara)

    # función auxiliar para calcular el histograma de bordes por zonas
    def bordes_por_zona(angulos, mascara):
        num_zonas_x = 4
        num_zonas_y = 4
        num_bins_por_zona = 10
        descriptor = []

        # función auxiliar para calcular los ángulos en una zona
        def angulos_en_zona(angulos, mascara):
            angulos_zona = []
            for row in range(mascara.shape[0]):
                for col in range(mascara.shape[1]):
                    if not mascara[row][col]:
                        continue
                    angulo = round(math.degrees(angulos[row][col]))
                    # dejar angulos en el rango -90 a 90
                    if angulo < -180 or angulo > 180:
                        raise Exception("angulo invalido {}, verificar si funciona la mascara".format(angulo))
                    elif angulo <= -90:
                        angulo += 180
                    elif angulo > 90:
                        angulo -= 180
                    angulos_zona.append(angulo)
            return angulos_zona

        for j in range(num_zonas_y):
            desde_y = int(mascara.shape[0] / num_zonas_y * j)
            hasta_y = int(mascara.shape[0] / num_zonas_y * (j + 1))
            for i in range(num_zonas_x):
                desde_x = int(mascara.shape[1] / num_zonas_x * i)
                hasta_x = int(mascara.shape[1] / num_zonas_x * (i + 1))
                angulos_zona = angulos_en_zona(angulos[desde_y:hasta_y, desde_x:hasta_x],
                                            mascara[desde_y:hasta_y, desde_x:hasta_x])
                histograma, _ = numpy.histogram(angulos_zona, bins=num_bins_por_zona, range=(-90, 90))
                if numpy.sum(histograma) != 0:
                    histograma = histograma / numpy.sum(histograma)
                descriptor.extend(histograma)
        return numpy.array(descriptor, dtype=numpy.float32)

    return bordes_por_zona(angulos, mascara)
# ---- Fin de funciones descriptoras ----

def tarea1_parte1(dir_input_imagenes_R, dir_output_descriptores_R):
    if not os.path.isdir(dir_input_imagenes_R):
        print("ERROR: no existe directorio {}".format(dir_input_imagenes_R))
        sys.exit(1)
    elif os.path.exists(dir_output_descriptores_R):
        print("ERROR: ya existe directorio {}".format(dir_output_descriptores_R))
        sys.exit(1)
    # Implementar la fase offline

    # 1-leer imágenes en dir_input_imagenes
    # puede servir la funcion util.listar_archivos_con_extension() que está definida en util.py
    imagenes = util.listar_archivos_con_extension(dir_input_imagenes_R, ".jpg")

    # 2-calcular descriptores de imágenes
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

    descriptores = calcular_descriptores(descriptor_intensidades, imagenes, dir_input_imagenes_R)

    # 3-escribir en dir_output_descriptores_R los descriptores calculados en uno o más archivos
    # puede servir la funcion util.guardar_objeto() que está definida en util.py
    util.guardar_objeto(descriptores, dir_output_descriptores_R, "descriptores.pkl")

# inicio de la tarea
if len(sys.argv) < 3:
    print("Uso: {}  dir_input_imagenes_R  dir_output_descriptores_R".format(sys.argv[0]))
    sys.exit(1)

# lee los parametros de entrada
dir_input_imagenes_R = sys.argv[1]
dir_output_descriptores_R = sys.argv[2]

# ejecuta la tarea
tarea1_parte1(dir_input_imagenes_R, dir_output_descriptores_R)
