# Tarea 1 | Entrega Parcial 1

## Proyecto

El objetivo de esta primera entrega, es sentar las entidades y estructuras necesarias para
el videojuego. Es decir, no deberá crear interacciones entre estas, sino que solo lo que
una instancia de alguna clase sea capaz de hacer por sí sola. Tampoco deberá preocuparse
por el momento sobre los casos excepcionales, pues serán solicitados en otro momento.

Para guiarles en este proceso, contará con una lista de requisitos a cumplir. Cada uno de
estos requisitos deberá estar reflejado en su proyecto al momento de realizar la entrega
final, tanto de manera funcional como testeada. El requisito en cuestión solo indica *qué*
es lo que debe hacer, sin embargo, el *cómo* hacerlo de la mejor manera recaerá en usted.

## Unidades

### Personajes

- Un personaje debe tener un nombre, puntos de vida, defensa y peso.

- Un personaje mágico debe tener además puntos de maná.

- Un personaje puede tener las siguientes clases:
    - Caballero
    - Arquero
    - Ladrón
    - Mago Negro
    - Mago Blanco

- Un personaje debe tener un slot de arma. (Esto no hace referencia a que tenga un método
  que le permita equiparse una, solo la posibilidad de tenerla)
  
- Un personaje debe tener un inventario de utilizables.
  
### Enemigos

- Un enemigo debe tener un nombre, puntos de vida, ataque, defensa y peso.

## Jugadores

- Un jugador debe tener una lista de unidades.

- Un jugador debe saber si ha sido o no derrotado.

## Utilizables

### Armamento

- Un arma debe tener nombre, puntos de ataque, peso, y un dueño.

- Un arma mágica debe tener además puntos de ataque mágico.

- Un arma puede ser:
    - Espada
    - Daga
    - Arco
    - Varita
    - Bastón

### Pociones

- Una poción debe tener un nombre.

- Una poción puede ser de:
    - Curación
    - Fortaleza
    - Maná
    - Fuerza Mágica

**Recuerde que debe testear todas las funcionalidades que implemente.**

## Visualizador

Para esta entrega parcial, recibirá la interfaz GameObject. Lea su documentación, y
al momento de realizar la estructura de sus entidades del juego, asegúrese que la más
elemental de cada una extienda de GameObject para que sus clases concretas implementen los
métodos necesarios.

### toJson para Utilizables

El formato del visualizador para un utilizable es el siguiente:

```scala
JsObj(
  "id" -> id,
  "name" -> name
)
```

Donde `id` es el id del utilizable, y `name` el nombre del utilizable. Estos no serán
visibles hasta que se implementen las acciones, por lo que no debe preocuparse si no
aparecen en el visualizador.

## Git

Para la correcta inicialización de su trabajo en este proyecto, debe realizar las
siguientes instrucciones:

1. Seguir el enlace de *GitHub Classroom* que se les ha entregado para crear un
   repositorio privado con los archivos base del proyecto.

2. Clonar el repositorio en su computador. Para esto, utilice el comando
   ``git clone <url>``. Una vez situado en el directorio en el que trabajará.

**IMPORTANTE**: Usted debe abrir IntelliJ desde la carpeta que clonó, es decir, la que
tenga el nombre tarea-0-YourGithubName. Hacerlo desde la carpeta que contiene a
esta no permitirá el correcto funcionamiento de la aplicación.

3. Crear una rama llamada ``entrega-parcial-1``.

    - Para esto, utilice el comando ``git branch <branch_name>``. En este caso, sería
      ``git branch entrega-parcial-1``.

    - Para que su progreso pueda ser almacenado en dicha rama (y no en la rama main u
      otras), debe utilizar el comando ``git checkout <branch_name>``. En este caso, sería
      ``git checkout entrega-parcial-1``. A esto se le conoce comúnmente como
      "cambiarse de rama".
      
4. **IMPORTANTE**: Para avanzar en su proyecto, deberá realizar lo siguiente:

```bash
git remote add template <*>

git fetch template

git merge -X theirs template/T1 --allow-unrelated-histories
```

<*> = https://github.com/dcc-cc3002/template-2024-2.git si cuando clona repos usa HTTPS, o
      git@github.com:dcc-cc3002/template-2024-2.git si usa SSH. No añada las llaves.
      
Esto les permitirá recibir la interfaz GameObject.

*Tenga en cuenta que el cuerpo docente tiene acceso total a su repositorio.*

## Entrega

Para subir su Entrega Parcial, deberá crear un *pull request* desde la rama
``entrega-parcial-1`` a la rama ``main`` llamado ``Tarea 1 - Entrega Parcial 1``.

Es importante que **no hagan merge** de la rama ``entrega-parcial-1`` a la rama ``main``
para que el cuerpo docente pueda revisar su *pull request*.

Por *U-Cursos* debe entregar un único archivo llamado ``entrega-parcial-1.txt`` con el
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

