# Cómo crear un Json a partir de la librería `util.Json`

## Introducción: ¿Qué es un Json?

Es un formato de intercambio de datos que se utiliza para enviar información
entre un servidor y un cliente. En este caso, se utilizará para enviar
información entre el _back-end_ (sus tareas) y el _front-end_ (el visualizador
proporcionado) del proyecto.

En palabras más simples y menos técnicas, utilizaremos este formato (que es
esencialmente un diccionario) para decirle a nuestro visualizador qué es lo que
queremos que muestre en pantalla, y cómo.

## Creación de un Json usando la librería `util.Json`

Para crear un Json, se debe seguir la siguiente sintaxis:

```scala
// Importar esta línea siempre que declaren JsObj o JsArr!
import util.Json.{*, given}

val json: JsVal = JsObj(
  "key1" -> "value1",
  "key2" -> 2,
  "key3" -> JsArr(1, 2, 3),
  "key4" -> JsObj(
    "key4.1" -> "value2",
    "key4.2" -> 6
  )
)
```

Donde:
- `JsObj` es un objeto Json. Se utiliza para definir un objeto con pares de
  clave-valor. En este caso, se está definiendo un objeto con cuatro pares de
  clave-valor. Cada par de clave-valor se define con la sintaxis
  `"key" -> value`.
    - En el ejemplo, se definen cuatro pares de clave-valor:
        - `"key1" -> "value1"`: La clave es `"key1"` y el valor es `"value1"`.
        - `"key2" -> 2`: La clave es `"key2"` y el valor es `2`.
        - `"key3" -> JsArr(1, 2, 3)`: La clave es `"key3"` y el valor es un
        arreglo Json con los elementos `1`, `2` y `3`.
        - `"key4" -> JsObj(...)`: La clave es `"key4"` y el valor es un objeto
        Json con dos pares de clave-valor. Nótese que el valor puede ser sin
        problemas otro objeto Json.
        - `"key4.1" -> "value2"`: La clave es `"key4.1"` y el valor es
        `"value2"`.
        - `"key4.2" -> 6`: La clave es `"key4.2"` y el valor es `6`.

- `JsArr` es un arreglo Json. Lo más importante a tener en cuenta aquí, es que
  los elementos de un arreglo Json deben ser del mismo tipo. Particularmente,
  utilizaremos JsArr para definir arreglos de objetos Json (JsObj).

## Creación de una unidad del juego usando la librería `util.Json`

Para crear una unidad del juego, se debe seguir la siguiente especificación al
momento de crear el objeto Json:

```scala
val character1: JsObj = JsObj(
  "id" -> "c1", 
  "attributes" -> JsArr(
    JsObj("name" -> "name", "value" -> "Character 1"),
    JsObj("name" -> "hp", "value" -> "20"),
  ),
  "img" -> "Terra.gif"
)
```

Donde:
- La clave `"id"` es el identificador **único** de la unidad.

- La clave `"attributes"` es un arreglo de objetos Json. Cada objeto Json
  representa un atributo de la unidad. En este caso, se definen dos atributos:
    - `"name" -> "name"`: La clave es `"name"` y el valor es `"Character 1"`.
    - `"hp" -> "20"`: La clave es `"hp"` y el valor es `"20"`.
  Esto se utilizará para expresar qué atributos se mostrarán en el visualizador.
  Por ejemplo, en este caso se mostrará el nombre como `NAME` y la vida como
  `HP` de la unidad.
  A efectos prácticos, puede agregar todos los atributos que desee, pero tenga
  en consideración que si agrega demasiados atributos, el visualizador no se
  verá muy bien por el espacio limitado que tiene.

- La clave `"img"` es la imagen que se mostrará de la unidad en el visualizador.
  En este caso, se muestra la imagen `Terra.gif`, que se encuentra en la carpeta
  `resources`. Toda imagen que desee mostrar en el visualizador debe estar en
  dicha carpeta. Considere que la imagen debe tener un tamaño ajustado para no
  desconfigurar el visualizador. Guíese por los tamaños de las imágenes que se
  entregan como ejemplo.

## Creación de un panel del juego usando la librería `util.Json`

Para crear un panel del juego, se debe seguir la siguiente especificación al
momento de crear el objeto Json:

```scala
val panel1: JsObj = JsObj(
  "id" -> "p1",
  "x" -> 1,
  "y" -> 1,
  "storage" -> JsArr(
    "c1"
  )
)
```

Donde:
- La clave `"id"` es el identificador **único** del panel.

- Las claves `"x"` y `"y"` son las coordenadas del panel en el tablero. En este
  caso, el panel se encuentra en la posición `(1, 1)`. Para el visualizador,
  esta es la coordenada origen, es decir, la esquina superior izquierda. El
  resto de coordenadas se verán como `(x, -y)` desde un punto de vista de un
  plano cartesiano.

- La clave `"storage"` es un arreglo de identificadores de unidades. En este
  caso, el panel contiene a la unidad con identificador `"c1"`. Si un panel
  no contiene a ninguna unidad, el arreglo debe estar vacío, es decir,
  `"storage" -> JsArr()`, no definir la clave `"storage"` generará errores en el
  visualizador.

## Creación de un jugador del juego usando la librería `util.Json`

Para crear un jugador del juego, se debe seguir la siguiente especificación al
momento de crear el objeto Json:

```scala
val player1: JsObj = JsObj(
  "characters" -> JsArr(
    character1
  )
)

val player2: JsObj = JsObj(
  "characters" -> JsArr(
    character2,
    character3
  )
)
```

Donde:
- La clave `"characters"` es un arreglo de objetos Json. Cada objeto Json
  representa una unidad del jugador. En este caso, se definen dos jugadores:
    - `player1` tiene una unidad.
    - `player2` tiene dos unidades.

Al momento de implementar el método `getPlayers`, deberá retornar un JsArr de
jugadores. Soporta estrictamente solo dos jugadores.
