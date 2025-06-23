# Tarea 2 | Entrega Final

## Descripción de la entrega

El objetivo de esta tarea es expandir el sistema de combate por turnos previamente diseñado. En esta entrega, se implementan las **interacciones entre entidades** en el juego, gestionadas mediante un sistema de acciones centralizado. Además, se ha añadido hecho el anclaje del modelo con el controlador `GameController` que permite manejar el flujo del juego y asegurar que cada unidad actúe en el momento correcto de manera dinámica a diferencia del ejemplo de uso que usabamos previamente.

Esta entrega también establece un conjunto de **restricciones específicas para ciertas acciones**, verificando condiciones antes de que estas se ejecuten para mantener la coherencia de las interacciones de juego.

### Funcionalidades Principales

- **Sistema de Acciones**: El sistema se basa en el método `doAction`, que permite a una entidad aplicar una acción sobre otra. Las acciones dependen de los tipos específicos de la fuente (`Source`), de la acción, y del objetivo (`Target`).

- **Control de Turnos**: `GameController` gestiona el flujo de turnos y expone métodos como `getCurrentGameUnitId` y `decideNextGameUnitId` para el manejo secuencial de las unidades en juego.

- **Restricciones de Juego**: Se han implementado exceptions especiales para verificar que las acciones cumplan con ciertas reglas antes de ejecutarse. Entre las principales restricciones se incluyen:
    - Las unidades deben moverse solo a paneles aledaños.
    - Una unidad requiere puntos de vida positivos para actuar.
    - Los personajes mágicos deben cumplir con requisitos de puntos de maná al lanzar hechizos.
    - Purificación solo afecta a enemigos, mientras que Curación solo puede aplicarse a personajes.
    - Los personajes deben portar un arma para realizar ataques y, si son mágicos, un arma mágica para realizar hechizos.

### Decisiones de Implementación

Dentro de los varios cambios que hice desde la entrega final 1 los más destacables fueron
1. Dejar de usar listas para los casos no dinámicos (definiciones) pero sí mutables, esto solo para saber de qué tipo de estructura se trata
2. Reemplazar todos los cases que tenía por double dispatch
3. Subir a las superclases el sistema de magia, puesto que así el double dispatch es directo, aunque sinceramente hubiese sido aún más directo hacerlo mediante generics ahorrando el breaking change si no fuese por el manejo de exceptions que es más engorroso