# Tarea 3 | Entrega Final 3

## Proyecto

El objetivo de esta última entrega final es formalizar la creación de las entidades del
juego mediante fábricas. Usando el patrón de diseño *factory*, deberá crear las fábricas
necesarias para que al momento de inicializarse un juego, su controlador recurra a estas
para generar los bandos, con sus respectivos recursos (vida, maná, armas, pociones, etc).

- Debe existir una fábrica de armas y pociones.

- Debe reconocer quién o quiénes son los clientes de estas fábricas.

- Debe existir una fábrica de personajes.

- Debe reconocer quién o quiénes son los clientes de esta fábrica.

- Debe existir un método (o si ya lo tenía, modificarlo) en el controlador que inicialice
  la creación de los personajes de cada bando y sus recursos de manera aleatoria.

**Recuerde que debe testear todas las funcionalidades que implemente.**

## Git

El trabajo realizado para esta entrega se debe realizar en una nueva rama que deberá
llamar ``entrega-final-3``:

1. Asegúrese de estar en la rama más reciente de su trabajo. Puede usar el comando
``git checkout <last_branch>`` para cambiarse de rama, donde `<last_branch>` es la
rama de su última entrega.

2. Utilice el comando ``git branch <branch_name>`` para crear la rama. En este caso, sería
``git branch entrega-final-3``.

3. Recuerde que para que su progreso pueda ser almacenado en dicha rama, debe cambiarse de
rama utilizando el comando ``git checkout <branch_name>``. En este caso, sería
``git checkout entrega-final-3``.

*Tenga en cuenta que el cuerpo docente tiene acceso total a su repositorio.*

## Entrega

Para subir su Entrega Parcial, deberá crear un *pull request* desde la rama
``entrega-final-3`` a la rama ``main`` llamado ``Tarea 3 - Entrega Final``.

Es importante que **no hagan merge** de la rama ``entrega-final-3`` a la rama ``main`` 
para que el cuerpo docente pueda revisar su *pull request*.

Por *U-Cursos* debe entregar un único archivo llamado ``entrega-final-3.txt`` con el
siguiente contenido:

```
Nombre: <Nombre completo>
Pull Request: <Link del pull request>
```

No cumplir con el formato pedido de una Entrega Parcial podría llevar a no ser
considerada, y para una Entrega Final, tiene descuentos en su nota final.
