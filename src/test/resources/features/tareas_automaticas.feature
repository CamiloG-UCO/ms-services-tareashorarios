# language: es
Característica: Tareas automáticas
  Como sistema
  Quiero crear una tarea automática de limpieza cuando un huésped realice check-out
  Para mantener las habitaciones disponibles rápidamente

  Escenario: Crear tarea post check-out
    Dado la habitación "H-456" del hotel "Santa Marta Resort" cambie a estado "Disponible" tras check-out
    Cuando el sistema detecte la liberación
    Entonces debe crear la tarea "Limpieza habitación H-456" asignada a "Ana Torres"
    Y registrar el tiempo estimado de 30 minutos
