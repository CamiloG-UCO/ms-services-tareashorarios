Feature: Control de asistencia y cumplimiento de turno
  Como supervisor,
  Quiero que el sistema registre automaticamente la hora de entrada y salida de los empleados,
  Para evaluar cumplimiento y puntualidad.

  Scenario: Registro correcto de entrada y salida
    Given el empleado "Carlos Díaz" tiene turno asignado de "08:00" a "16:00" el dia "2025-11-03"
    When el usuario registra su llegada con codigo o tarjeta a las "08:05"
    Then el sistema debe marcar "Entrada registrada con 5 minutos de retraso"
    When registra salida a las "16:02"
    Then el sistema debe calcular el total trabajado "7h 57m"
    And guardar el registro en el historial de asistencia del empleado