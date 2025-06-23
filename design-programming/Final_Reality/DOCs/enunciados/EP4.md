# Tarea 2 | Entrega Parcial 4

## Proyecto

El objetivo de esta entrega parcial es implementar restricciones específicas para ciertas 
funcionalidades del juego. Si la restricción no es cumplida, deberá lanzar una excepción que
indique en su mensaje el motivo del fallo. Este mensaje está hecho para que quien programa
entienda el motivo de la caída del programa debido a esta.

A continuación se le hace entrega de dichas restricciones a implementar:

- Cuando una unidad intenta moverse a un panel, el panel al que se desplaza debe ser uno de 
  los paneles aledaños a él.

- Una unidad debe tener una cantidad positiva de puntos de vida para poder realizar
  cualquier acción.

- Un personaje mágico debe cumplir con los puntos de maná necesario al momento de castear un
  hechizo.

- Una unidad que está derrotada no debe ser capaz de recibir acciones.

- Purificación debe ser recibida únicamente por enemigos.

- Curación debe ser recibida únicamente por personajes.

- Un personaje debe tener un arma para poder realizar un ataque.

- Un personaje mágico además debe tener un arma mágica para poder realizar hechizos.

- Los personajes pueden equiparse únicamente las armas designadas por el cuadro del enunciado.

**Recuerde que debe testear todas las funcionalidades que implemente.**
  
## Visualizador

No hay nuevas instrucciones para el visualizador.

Note que el método `doAction` de la interfaz `IGameController` también retorna un String. 
Esto significa que, en caso de que una acción no sea ejecutada correctamente, el mensaje a 
mostrar debería ser un mensaje que permita al jugador entender por qué sucedió dicha 
excepción.

## Git

El trabajo realizado para esta entrega se debe realizar en una nueva rama que deberá
llamar ``entrega-parcial-4``:

1. Asegúrese de estar en la rama más reciente de su trabajo. Puede usar el comando
``git checkout <last_branch>`` para cambiarse de rama, donde `<last_branch>` es la
rama de su última entrega.

2. Utilice el comando ``git branch <branch_name>`` para crear la rama. En este caso, sería
``git branch entrega-parcial-4``.

3. Recuerde que para que su progreso pueda ser almacenado en dicha rama, debe cambiarse de
rama utilizando el comando ``git checkout <branch_name>``. En este caso, sería
``git checkout entrega-parcial-4``.

*Tenga en cuenta que el cuerpo docente tiene acceso total a su repositorio.*

## Entrega

Para subir su Entrega Parcial, deberá crear un *pull request* desde la rama
``entrega-parcial-4`` a la rama ``main`` llamado ``Tarea 2 - Entrega Parcial 4``.

Es importante que **no hagan merge** de la rama ``entrega-parcial-4`` a la rama ``main`` 
para que el cuerpo docente pueda revisar su *pull request*.

Por *U-Cursos* debe entregar un único archivo llamado ``entrega-parcial-4.txt`` con el
siguiente contenido:

```
Nombre: <Nombre completo>
Pull Request: <Link del pull request>
```

No cumplir con el formato pedido de una Entrega Parcial podría llevar a no ser
considerada, y para una Entrega Final, tiene descuentos en su nota final.
