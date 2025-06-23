# Tarea 3 | Entrega Final

## Descripción del Proyecto
En esta entrega, se implementan dos aspectos esenciales para completar el sistema de combate y mejorar su extensibilidad:

    - Finalización del juego: Integración de un mecanismo para detectar cuándo un grupo de unidades ha sido derrotado y notificar al controlador sobre el fin del juego. Esto incluye la entrega de un mensaje distintivo que indique la conclusión.

    - Creación formal de entidades: Uso del patrón de diseño factory para la creación de personajes, armas y recursos de manera estructurada y reutilizable, con el objetivo de inicializar los bandos del juego de forma automatizada.

## Funcionalidades Principales

El sistema implementado cumple con los siguientes requisitos:

1. Finalización del Juego

2. Detección de derrota: El sistema identifica automáticamente cuándo un grupo de unidades ha sido completamente derrotado.

3. Notificación al controlador: Se comunica al controlador la finalización del juego con un mensaje específico según el bando ganador.

4. Creación de Entidades
    - Fábrica de Armas y Pociones: Genera armas y pociones con atributos específicos para los personajes.
    - Fábrica de Personajes: Produce personajes asignados a cada bando, configurando sus recursos (vida, maná, armas, etc.) de manera coherente.
    - Método de inicialización en el controlador: Permite crear aleatoriamente los personajes y recursos de cada bando mediante las fábricas definidas.

## Comentarios semi importantes

Para esta entrega, las fábricas y los métodos asociados han sido diseñados para permitir flexibilidad y adaptabilidad en futuras expansiones del juego. Acerca de las configuraciones aleatorias tenía pensado utilizar distribuciones beta o normal en lugar de las uniformes que no son tan justas. Finalmente decidí rendirme y no implementar los test que usaban semillas por temas de tiempo

### Cambios importantes desde la última entrega

1. **Refactorización del Sistema de Acciones**:
    - Reimplementé el método `doAction` para centralizar la gestión de acciones entre entidades. Ahora sí es posible mantener un flujo consistente de turnos (previamente ocurría un error al cambiar de player)

2. **Factories Personalizadas**:
    - En lugar de usar una sola factory, decidí implementar clases concretas para que cada entidad tenga sus estadísticas más fieles a lo que uno esperaría en un rpg,
    - Destacar que para el manejo de IDs tuve que hacer uso de herramientas poco legales con hash