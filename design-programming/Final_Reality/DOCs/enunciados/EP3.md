# Tarea 2 | Entrega Parcial 3

## Proyecto

El objetivo de esta entrega parcial es implementar el sistema de interacciones entre las
entidades que han creado hasta ahora. Recibirá nuevos *traits* que le permitirán conectar
las acciones diseñadas con las entidades que poseen y ejecutan acciones (`Source`) y
aquellas entidades que reciben dichas acciones (`Target`).

Adicionalmente, podrá poner en marcha su sistema de turnos en su `GameController`. Junto
con los nuevos *traits* a recibir, la interfaz `IGameController` tendrá modificaciones que
implican que usted deberá implementar los métodos que define.

### Sistema de acciones

Identifique las clases en su estructura que deberían ser fuentes de acciones, y/u
objetivos de estas, y extienda apropiadamente la estructura con los traits entregados
según corresponda.

El sistema entero de acciones se basa en su método más esencial: `doAction`. Dada una
acción y un objetivo de acción, el objetivo recibirá los efectos que indique dicha acción.
Nótese que esto dependerá tanto del tipo concreto del Source, del tipo concreto de la
acción, y del tipo concreto del Target. El desafío de esta entrega parcial (y el más
importante de la Tarea 2) consiste en lograr desambiguar el tipo de estas 3 entidades.

**Importante**: La acción que tenga designada a mover a una unidad, deberá tener
estrictamente el id `"2"`. De otra manera no podrá desplazar a unidades por el mapa.

**Recuerde que debe testear todas las funcionalidades que implemente.**

### IGameController

La nueva versión de la interfaz `IGameController` implicará que deberá implementar los
siguientes métodos:

- `getCurrentGameUnitId`
- `decideNextGameUnitId`
- `findActionsByGameUnitId`
- `doAction`
- `reset`

Esto permitirá principalmente al visualizador (y a usted) poder llevar un conteo oficial
del flujo de turnos del juego*.

*Por ahora considere que el juego es un loop infinito de turnos, cómo finalizar el juego
se verá más adelante.
  
## Visualizador

Finalmente, lo que llevan tanto tiempo esperando: ¡es momento de poner en *acción* el
sistema de acciones!

La unidad que esté en turno deberá entregar al método `findActionsByGameUnitId` el
siguiente JsObj: `JsObj("actions" -> JsArr(unit.actions.map(_.toJson)))`. Para esto,
nótese que deberá buscar mediante el id entregado a la unidad en cuestión.

El visualizador desplegará las acciones en el menú central, donde usted podrá clickear
en dicha acción, y aplicarla al objetivo correspondiente**.

**De momento, si realiza de manera correcta el proceso para ejecutar un meteorito, el
visualizador no soporta actualmente su funcionamiento. Se hará una actualización a este y
se dará aviso para que realice *pull* del repositorio.

## Git

El trabajo realizado para esta entrega se debe realizar en una nueva rama que deberá
llamar ``entrega-parcial-3``:

1. Asegúrese de estar en la rama más reciente de su trabajo. Puede usar el comando
``git checkout <last_branch>`` para cambiarse de rama, donde `<last_branch>` es la
rama de su última entrega.

2. Utilice el comando ``git branch <branch_name>`` para crear la rama. En este caso, sería
``git branch entrega-parcial-3``.

3. Recuerde que para que su progreso pueda ser almacenado en dicha rama, debe cambiarse de
rama utilizando el comando ``git checkout <branch_name>``. En este caso, sería
``git checkout entrega-parcial-3``.

4. **IMPORTANTE**: Para avanzar en su proyecto, deberá realizar lo siguiente:

```bash
git fetch template

git merge -X theirs template/T2 --allow-unrelated-histories
```

<*> = https://github.com/dcc-cc3002/template-2024-2.git si cuando clona repos usa HTTPS, o
      git@github.com:dcc-cc3002/template-2024-2.git si usa SSH. No añada las llaves.
      
Esto les permitirá recibir la interfaz `Source`, `Target` y las modificaciones mencionadas
para `IGameController`.

*Tenga en cuenta que el cuerpo docente tiene acceso total a su repositorio.*

## Entrega

Para subir su Entrega Parcial, deberá crear un *pull request* desde la rama
``entrega-parcial-3`` a la rama ``main`` llamado ``Tarea 2 - Entrega Parcial 3``.

Es importante que **no hagan merge** de la rama ``entrega-parcial-3`` a la rama ``main`` 
para que el cuerpo docente pueda revisar su *pull request*.

Por *U-Cursos* debe entregar un único archivo llamado ``entrega-parcial-3.txt`` con el
siguiente contenido:

```
Nombre: <Nombre completo>
Pull Request: <Link del pull request>
```

No cumplir con el formato pedido de una Entrega Parcial podría llevar a no ser
considerada, y para una Entrega Final, tiene descuentos en su nota final.

