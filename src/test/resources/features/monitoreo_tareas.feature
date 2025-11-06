# language: es
Característica: Monitoreo de tareas
  Como administrador
  Quiero consultar y actualizar las tareas del sistema
  Para tener control del progreso y estado de las mismas

  Escenario: Consultar todas las tareas registradas
    Dado existen tareas registradas en el sistema
    Cuando el administrador consulte todas las tareas
    Entonces el sistema debe mostrar la lista completa de tareas con su estado actual

  Escenario: Consultar tareas pendientes
    Dado existen tareas con diferentes estados
    Cuando el administrador consulte las tareas con estado "Pendiente"
    Entonces el sistema debe mostrar solo las tareas que están pendientes

  Escenario: Actualizar estado de una tarea
    Dado una tarea pendiente registrada
    Cuando el administrador actualice su estado a "Completada"
    Entonces el sistema debe reflejar el cambio correctamente
