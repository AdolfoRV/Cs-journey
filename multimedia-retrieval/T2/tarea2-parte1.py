# CC5213 - TAREA 2 - RECUPERACIÓN DE INFORMACIÓN MULTIMEDIA
# Fecha: 08 de mayo de 2025
# Alumno: Adolfo I. Rojas Valenzuela

import sys
import os
import util as util
import librosa
import numpy

# Preliminar: funciones para calcular MFCC
def lista_ventanas(nombre_archivo, numero_descriptores, sample_rate, samples_por_ventana):
    # tantas ventanas como numero de descriptores
    tiempos = []
    for i in range(0, samples_por_ventana * numero_descriptores, samples_por_ventana):
        # tiempo de inicio de la ventana
        segundos_desde = i / sample_rate
        # tiempo de fin de la ventana
        segundos_hasta = (i + samples_por_ventana - 1) / sample_rate
        # crear diccionario con metadata de la ventana
        ventana = {
            'nombre_archivo': nombre_archivo,
            'segundos_desde': segundos_desde,
            'segundos_hasta': segundos_hasta
        }
        # agregar a la lista
        tiempos.append(ventana)
    return tiempos

def calcular_mfcc_archivo(archivo_audio, sample_rate, samples_por_ventana, samples_salto, dimension, carpeta_temporal):
    archivo_wav = util.convertir_a_wav(archivo_audio, sample_rate, carpeta_temporal)
    
    # Leer audio
    samples, sr = librosa.load(archivo_wav, sr=None)
    print("  audio samples={} samplerate={} segundos={:.1f}".format(len(samples), sr, len(samples) / sr))
    
    # Calcular MFCC
    mfcc = librosa.feature.mfcc(y=samples, sr=sr, n_mfcc=dimension, n_fft=samples_por_ventana, hop_length=samples_salto)
    descriptores = mfcc.transpose()
    
    # Eliminar la primera dimensión (energía global)
    descriptores = descriptores[:, 1:]
    return descriptores

def calcular_mfcc_varios_archivos(lista_archivos, sample_rate, samples_por_ventana, samples_salto, dimension, carpeta_temporal):
    descriptores_mfcc = []
    descriptores_ventanas = []
    for nombre in lista_archivos:
        nombre_archivo = os.path.join(carpeta_entrada_audios, nombre)
        audio_mfcc = calcular_mfcc_archivo(nombre_archivo, sample_rate, samples_por_ventana, samples_salto, dimension, carpeta_temporal)
        audio_ventanas = lista_ventanas(nombre_archivo, audio_mfcc.shape[0], sample_rate, samples_por_ventana)

        if len(descriptores_mfcc) == 0:
            descriptores_mfcc = audio_mfcc
        else:
            # agregar como filas
            descriptores_mfcc = numpy.vstack([descriptores_mfcc, audio_mfcc])

            util.guardar_objeto(audio_mfcc, carpeta_temporal, f"{nombre}.pkl")
        # agregar al final
        descriptores_ventanas.extend(audio_ventanas)
        util.guardar_objeto(audio_ventanas, carpeta_temporal, f"{nombre}_ventanas.pkl") 
    return descriptores_ventanas, descriptores_mfcc

# ------------------------------------------------------------------------
def tarea2_parte1(carpeta_entrada_audios, carpeta_salida_descriptores):
    if not os.path.isdir(carpeta_entrada_audios):
        print("ERROR: no existe carpeta {}".format(carpeta_entrada_audios))
        sys.exit(1)
    elif os.path.exists(carpeta_salida_descriptores):
        print("ERROR: ya existe {}".format(carpeta_salida_descriptores))
        sys.exit(1)
    
    sample_rate = 44100         # calidad del audio
    samples_por_ventana = 4096  # tamaño de ventana
    samples_salto = 4096        # salto entre ventanas  
    dimension = 13              # dimensión del descriptor MFCC (incluyendo energía que se eliminará)
   
    #  2-convertir cada archivo de audio a wav (guardar los wav temporales en carpeta_salida_descriptores)
    #    puede servir la funcion util.convertir_a_wav() que está definida en util.py
    archivos_m4a = util.listar_archivos_con_extension(carpeta_entrada_audios, ".m4a")
    
    #  3-calcular descriptores del archivo wav
    descriptores_ventanas, descriptores_mfcc = calcular_mfcc_varios_archivos(archivos_m4a, sample_rate, samples_por_ventana, samples_salto, dimension, carpeta_salida_descriptores)

    #  4-escribir en carpeta_salida_descriptores los descriptores de cada archivo
    #    puede servir la funcion util.guardar_objeto() que está definida en util.py
    util.guardar_objeto(descriptores_mfcc, carpeta_salida_descriptores, "descriptor_mfcc_global.pkl")
    util.guardar_objeto(descriptores_ventanas, carpeta_salida_descriptores, "descriptor_ventanas_global.pkl")

# inicio de la tarea
if len(sys.argv) != 3:
    print("Uso: {} [carpeta_entrada_audios] [carpeta_salida_descriptores]".format(sys.argv[0]))
    sys.exit(1)

# lee los parametros de entrada
carpeta_entrada_audios = sys.argv[1]
carpeta_salida_descriptores = sys.argv[2]

# llamar a la tarea
tarea2_parte1(carpeta_entrada_audios, carpeta_salida_descriptores)