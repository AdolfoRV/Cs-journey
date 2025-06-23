# Tarea 1 | Entrega Final

## Proyecto

El objetivo de esta entrega final es sentar las bases detrás del sistema de combate el
cual se basa en dar dinámicamente el turno a los personajes en juego en base a su peso y
el de su arma. No deberá crear el flujo del sistema como tal, si no sólo los elementos y
mecánicas que existen detrás para en un futuro solo tener que conectarlo todo. Para ello
deberá crear un **Programador de Turnos** (o Turn Scheduler).

Al igual que la entrega pasada, contará con una lista de requisitos a cumplir. Cada uno de
estos requisitos deberá estar reflejado en su proyecto al momento de realizar la entrega
final, tanto de manera funcional como testeada. El requisito en cuestión solo indica *qué*
es lo que debe hacer, sin embargo, el *cómo* hacerlo de la mejor manera recaerá en usted.

### Sistema de combate

- Debe existir un programador de turnos.

- Se debe poder añadir y quitar unidades al programador de turnos.

- El programador debe poder calcular el máximo de la barra de acción de todas las
  unidades.

- El programador debe poder llevar registro de la barra de acción que llevan las unidades
  en batalla.

- El programador debe poder reiniciar la barra de acción de cada unidad.

- El programador debe poder aumentar simultáneamente la barra de acción de todas las
  unidades en batalla en una cantidad arbitraria.

- El programador debe ser capaz de señalar si una unidad completó su barra de acción.

- El programador debe ser capaz de entregar todas las unidades que completaron su barra de
  acción en orden.
  
  - Este orden debe ser de mayor a menor, basado en el excedente que las unidades tuvieron
    al completar su barra de acción.

- **El programador debe señalar a una única unidad a la que le corresponde el turno.**


Como indicación general, recuerde usar las estructuras de datos que Scala de manera nativa
da a su disposición.

**Recuerde que debe testear todas las funcionalidades que implemente.**

### Privacidad

- Se debe definir la privacidad de los atributos y métodos de distintas clases,
  determinando cuáles debiesen ser públicos, protegidos y privados.
  
## Visualizador

No existen nuevas indicaciones para el visualizador.

A modo de adelanto para las siguientes entregas, si revisan los métodos definidos en el
trait `IGameController`, existen dos que están fuertemente ligados al programador de
turnos: `getCurrentGameUnitId`, y `decideNextGameUnitId`. El primero debe retornar en todo
momento el id de la unidad que está jugando actualmente, mientras que la segunda se llama
cuando una acción es ejecutada, independiente de si fue exitosa o no, es decir, si la
acción fue exitosa, `decideNextGameUnitId` debe modificar quién es la unidad actual, y en
caso contrario, dejar la misma que ya estaba jugando.

## Git

El trabajo realizado para esta entrega se debe realizar en una nueva rama que deberá
llamar ``entrega-final-1``:

1. Asegúrese de estar en la rama más reciente de su trabajo. Puede usar el comando
``git checkout <last_branch>`` para cambiarse de rama, donde `<last_branch>` es la
rama de su última entrega.

2. Utilice el comando ``git branch <branch_name>`` para crear la rama. En este caso, sería
``git branch entrega-final-1``.

3. Recuerde que para que su progreso pueda ser almacenado en dicha rama, debe cambiarse de
rama utilizando el comando ``git checkout <branch_name>``. En este caso, sería
``git checkout entrega-final-1``.

*Tenga en cuenta que el cuerpo docente tiene acceso total a su repositorio.*

## Entrega

Para subir su Entrega Parcial, deberá crear un *pull request* desde la rama
``entrega-final-1`` a la rama ``main`` llamado ``Tarea 1 - Entrega Final``.

Es importante que **no hagan merge** de la rama ``entrega-final-1`` a la rama ``main`` 
para que el cuerpo docente pueda revisar su *pull request*.

Por *U-Cursos* debe entregar un único archivo llamado ``entrega-final-1.txt`` con el
siguiente contenido:

```
Nombre: <Nombre completo>
Pull Request: <Link del pull request>
```

No cumplir con el formato pedido de una Entrega Parcial podría llevar a no ser
considerada, y para una Entrega Final, tiene descuentos en su nota final.

