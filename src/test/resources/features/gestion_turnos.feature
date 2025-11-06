Feature: Gestión de turnos
  Como supervisor, quiero asignar turnos a cada empleado por semana, para organizar las tareas de limpieza y recepción

  Scenario: Crear plan semanal
    Given los empleados "Ana Torres, Carlos Díaz"
    When el supervisor asigne turnos del "2025-11-01" al "2025-11-07"
    Then el sistema debe registrar los horarios y enviar notificaciones a cada empleado

  Scenario: Crear plan semanal para otros empleados
    Given los empleados "Luis Pérez, Marta Gómez"
    When el supervisor asigne turnos del "2025-12-01" al "2025-12-07"
    Then el sistema debe registrar los horarios y enviar notificaciones a cada empleado

  Scenario: Crear plan semanal con un solo empleado
    Given los empleados "Sofía Ruiz"
    When el supervisor asigne turnos del "2026-01-01" al "2026-01-07"
    Then el sistema debe registrar los horarios y enviar notificaciones a cada empleado

  Scenario: Crear plan semanal con fechas diferentes
    Given los empleados "Pedro López, Laura Castro"
    When el supervisor asigne turnos del "2026-02-10" al "2026-02-16"
    Then el sistema debe registrar los horarios y enviar notificaciones a cada empleado

  Scenario: Rechazar asignación por empleados duplicados
    Given los empleados "Ana Torres"
    When el supervisor asigne turnos del "2025-10-01" al "2025-10-07"
    Then el sistema debe rechazar la asignación por empleados duplicados