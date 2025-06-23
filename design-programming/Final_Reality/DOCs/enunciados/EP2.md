# Tarea 1 | Entrega Parcial 2

## Proyecto

El objetivo de esta segunda entrega es:

1. Generar la estructura/jerarquía base de las acciones (4.3), sin considerar de momento
la existencia de fuentes (4.1) y objetivos (4.2) de estas.

2. Modelar el mapa del juego a través del sistema de paneles.

Al igual que la entrega pasada, **no deberá crear interacciones entre sus clases**, sino 
que solo lo que una instancia de alguna clase sea capaz de hacer por sí sola. Tampoco 
deberá preocuparse por el momento sobre los casos excepcionales, pues serán solicitados en 
otro momento.

Al igual que la entrega pasada, contará con una lista de requisitos a cumplir. Cada uno de
estos requisitos deberá estar reflejado en su proyecto al momento de realizar la entrega
final, tanto de manera funcional como testeada. El requisito en cuestión solo indica *qué*
es lo que debe hacer, sin embargo, el *cómo* hacerlo de la mejor manera recaerá en usted.

## Mapa del juego

- Un panel debe tener coordenadas (x, y).

- Un panel debe poseer una lista de unidades que se encuentran en su panel.

- Debe existir una manera de que un panel reconozca cuáles son sus paneles aledaños.
  Existen varias maneras de representar esto, por lo que haga fluir su imaginación y no se
  preocupe en exceso de optimizarlo.

## Acciones

- Una acción debe tener un nombre.

- Una acción puede ser:
  - Atacar
  - Moverse
  - Equipar arma
  - Consumir poción
  - Trueno
  - Meteorito
  - Curación
  - Purificación
  
- Una acción que involucra utilizables además debe tener una lista con los posibles
  objetos que puede utilizar.
  
Es responsabilidad de usted generar la estructura de clases adecuada de acciones.

**Recuerde que debe testear todas las funcionalidades que implemente.**

## Visualizador

### toJson para Acciones

Para representar las acciones como un JsObj, deberá tener en cuenta la estructura de sus
acciones. Si una acción es la única de su categoría, no deberá indicarla. Si una acción es
parte de un conjunto de más acciones de una misma categoría, deberá seguir un formato
específico para indicarlo. Esto se realiza con la finalidad de separar en el visualizador
las acciones mediante un menú de sub-acciones.

El formato del visualizador para una acción estándar es el siguiente:

```scala
JsObj(
  "id" -> id,
  "action" -> "category→name"
)
```

Donde:

- `id` es el id de la acción.
- `category→name` el nombre de la acción, junto a la categoría de esa acción. Nótese el
  carácter especial (→) el cual es **estrictamente necesario** para el correcto
  funcionamiento del visualizador. Si lo considerara necesario, puede también anidar
  categorías: `category1→category2→name`.

El formato del visualizador para una acción que involucra utilizables es el siguiente:

```scala
JsObj(
  "id" -> id,
  "action" -> "category→name",
  "targets" -> JsArr(targets.map(_.toJson))
)
```

Donde:

- `targets` es un JsArr que provee todos los utilizables correspondientes a la acción. el
  código `targets.map(_.toJson)` es una *mauskerramienta misteriosa* que se explicará más
  adelante, pero básicamente le dice a la lista `targets`, que a cada elemento de ella
  (`_`) aplica el método toJson, y retorna una nueva lista con los JsObj de cada elemento.
  Pueden utilizar, si lo desea, esta *mauskerramienta misteriosa* en el resto de sus 
  métodos toJson, es bastante útil.

De momento, las acciones no serán visibles hasta que se implemente la interacción entre 
estas y sus fuentes, por lo que no debe preocuparse si no aparecen en el visualizador.

## Git

Considerando las indicaciones de la entrega anterior, debe crear una rama llamada
``entrega-parcial-2``:

1. Asegúrese de estar en la rama más reciente de su trabajo. Si realizó la entrega parcial
1 de manera correcta, la rama en la que debería estar es ``entrega-parcial-1``. Puede usar 
el comando ``git checkout entrega-parcial-1`` para cambiarse.

2. Utilice el comando ``git branch <branch_name>`` para crear la rama. En este caso, sería
``git branch entrega-parcial-2``.

3. Recuerde que para que su progreso pueda ser almacenado en dicha rama, debe cambiarse de 
rama  utilizar el comando ``git checkout <branch_name>``. En este caso, sería
``git checkout entrega-parcial-2``.

*Tenga en cuenta que el cuerpo docente tiene acceso total a su repositorio.*

## Entrega

Para subir su Entrega Parcial, deberá crear un *pull request* desde la rama
``entrega-parcial-2`` a la rama ``main`` llamado ``Tarea 1 - Entrega Parcial 2``.

Es importante que **no hagan merge** de la rama ``entrega-parcial-2`` a la rama ``main``
para que el cuerpo docente pueda revisar su *pull request*.

Por *U-Cursos* debe entregar un único archivo llamado ``entrega-parcial-2.txt`` con el
siguiente contenido:

```
Nombre: <Nombre completo>
Pull Request: <Link del pull request>
```

No cumplir con el formato pedido de una Entrega Parcial podría llevar a no ser
considerada, y para una Entrega Final, tiene descuentos en su nota final.

La realización de esta Entrega Parcial es **obligatoria** y su no entrega corresponde a un
descuento de 0.5 puntos de la nota final de la Tarea 1. Sin embargo, no es necesario que
esta cumpla inmediatamente con un diseño apropiado ni la funcionalidad solicitada al
completo, pero sí un avance evaluable.

