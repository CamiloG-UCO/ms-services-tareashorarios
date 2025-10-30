# language: es
Característica: Monitoreo de tareas en tiempo real
  Como administrador del hotel
  Quiero supervisar el estado de las tareas de limpieza y mantenimiento
  Para garantizar que se ejecuten de manera eficiente y sin demoras

  Escenario: Consultar todas las tareas registradas
    Dado existen tareas registradas en el sistema
    Cuando el administrador consulte todas las tareas
    Entonces el sistema debe mostrar la lista completa de tareas con su estado actual

  Escenario: Filtrar tareas por estado
    Dado existen tareas con diferentes estados
    Cuando el administrador consulte las tareas con estado "Pendiente"
    Entonces el sistema debe mostrar solo las tareas que están pendientes

  Escenario: Actualizar el estado de una tarea
    Dado una tarea pendiente con identificador "T001"
    Cuando el administrador actualice su estado a "Completada"
    Entonces el sistema debe reflejar el cambio correctamente
