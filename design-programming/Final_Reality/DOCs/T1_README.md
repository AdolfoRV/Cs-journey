# Tarea 1 | Entrega Final

## Descripción del Proyecto

Este proyecto establece las bases para un **sistema de combate por turnos** basado en un Programador de Turnos (**Turn Scheduler**) que asigna dinámicamente el turno a los personajes en función de su peso y el de su arma. El objetivo es implementar los elementos y mecánicas internas que permitirán que el sistema de combate funcione correctamente en el futuro. Aunque no se implementará el flujo completo del combate en esta etapa, los componentes fundamentales deben estar desarrollados y listos para ser conectados en fases posteriores.

### Funcionalidades Principales

El sistema de combate cumple con los siguientes requisitos:

- **Programador de Turnos**: Sistema que gestiona el orden de los turnos de las unidades en el combate.

- **Añadir y quitar unidades**: Las unidades deben poder añadirse o eliminarse del programador de turnos.

- **Cálculo de la barra de acción**: El programador debe calcular el máximo de la barra de acción de todas las unidades en batalla.

- **Registro de la barra de acción**: Llevar registro del progreso de la barra de acción de las unidades.

- **Reiniciar la barra de acción**: Debe ser posible reiniciar el progreso de la barra de acción de cada unidad.

- **Aumento simultáneo de barra**: Incrementar simultáneamente el progreso de la barra de acción de todas las unidades en una cantidad arbitraria.

- **Detección de unidades listas para actuar**: El sistema debe ser capaz de señalar cuándo una unidad ha completado su barra de acción.

- **Orden de ejecución**: El programador debe devolver las unidades que completaron su barra de acción en un orden específico (de mayor a menor excedente al completar la barra).

- **Asignación de turno**: El sistema debe identificar cuál unidad tiene el turno en curso.

### Comentario importante

- Para aumentar el coverage tuve que implementar test inservibles a futuro para el GameController, puesto que en verdad todavía no nos dedicamos a trabajar esa parte del juego así que está totalmente sujeto a cambio en las versiones venideras

## Decisiones de Implementación

En este apartado se encuentran las principales decisiones que tomé durante el desarrollo de este sistema y mi proyecto so far:

- **Uso de estructuras**: Opté por usar estructuras de datos mutables nativas de Scala (como `List` y `Map`) para manejar las unidades y la barra de acción, previamente usé Set() que también es nativa pero mejor trabajar con las más conocidas.

- **Modularidad del sistema**: Separé la lógica del programador de turnos en varios métodos para cada funcionalidad (añadir/quitar unidades, avanzar barras, etc.), respecto a la actionBar esta tenía pensado en dejarla como un atributo per entity de acceso público, pero en lugar de ello simplemente utilizo el getter de peso para saber cuál es el máximo de la barra de la entidad, esto está sujeto a cambio en próximas versiones. Sobre el resto de módulos, estos se encuentran altamente relacionados con lo que la modularidad está sujeta a cambios en próximas versiones para aumentar independencia entre clases 

- **Gestión de excepciones y errores**: Para esta entrega decidí no lanzar mensajes de error o excepciones en caso de ocurrir algún caso borde, puesto que es materia reciente 

- **Extensibilidad futura**: Aunque esta fase del proyecto no incluye el flujo completo del sistema de combate ni del juego, todas las clases e interfaces son en pro de la extensibilidad (en caso de que se quiera implementar más allá de lo pedido en el pdf, ej: poción peso pluma)