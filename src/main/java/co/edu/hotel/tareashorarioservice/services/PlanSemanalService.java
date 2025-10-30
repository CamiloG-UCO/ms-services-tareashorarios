package co.edu.hotel.tareashorarioservice.services;

import co.edu.hotel.tareashorarioservice.domain.Empleado;
import co.edu.hotel.tareashorarioservice.domain.NotificacionTurno;
import co.edu.hotel.tareashorarioservice.domain.Turno;
import co.edu.hotel.tareashorarioservice.repository.EmpleadoRepository;
import co.edu.hotel.tareashorarioservice.repository.NotificacionTurnoRepository;
import co.edu.hotel.tareashorarioservice.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PlanSemanalService {
    @Autowired
    private EmpleadoRepository empleadoRepository;
    @Autowired
    private TurnoRepository turnoRepository;
    @Autowired
    private NotificacionTurnoRepository notificacionTurnoRepository;

    public void asignarTurnos(List<String> nombresCompletos, LocalDate inicio, LocalDate fin) {
        LocalDate hoy = LocalDate.now();
        if (inicio == null || fin == null || !inicio.isBefore(fin)) {
            throw new IllegalArgumentException("Fechas de turno inválidas: inicio debe ser anterior a fin y no nulas.");
        }
        if (inicio.isBefore(hoy) || fin.isBefore(hoy)) {
            throw new IllegalArgumentException("No se pueden agendar turnos en fechas pasadas. Solo fechas futuras o actuales.");
        }
        if (nombresCompletos == null || nombresCompletos.isEmpty()) {
            throw new IllegalArgumentException("Debe especificar al menos un empleado.");
        }
        for (String nombreCompleto : nombresCompletos) {
            Optional<Empleado> empleadoOpt = empleadoRepository.findByNombreCompleto(nombreCompleto);
            if (empleadoOpt.isEmpty()) {
                throw new IllegalArgumentException("El empleado con nombre completo '" + nombreCompleto + "' no existe.");
            }
            Empleado empleado = empleadoOpt.get();
            // Validar disponibilidad del empleado
            List<Turno> turnosExistentes = turnoRepository.findByEmpleadoAndTurnoInicioBetween(empleado, inicio, fin);
            if (!turnosExistentes.isEmpty()) {
                throw new IllegalArgumentException("El empleado '" + nombreCompleto + "' ya tiene turnos asignados en ese rango de fechas.");
            }
            // Crear notificación y guardar
            String mensaje = nombreCompleto + " se te agendaron turnos desde " + inicio + " hasta " + fin;
            NotificacionTurno notificacion = new NotificacionTurno();
            notificacion.setEmpleado(empleado);
            notificacion.setMensaje(mensaje);
            notificacionTurnoRepository.save(notificacion);
            // Asignar turnos (ejemplo alternando tareas)
            LocalDate fecha = inicio;
            boolean limpieza = true;
            while (!fecha.isAfter(fin)) {
                String tarea = limpieza ? "Limpieza" : "Recepción";
                // Cada turno es de un solo día, así que turno_inicio = turno_final = fecha
                Turno turno = new Turno(empleado, fecha, fecha, tarea, notificacion);
                turnoRepository.save(turno);
                fecha = fecha.plusDays(1);
                limpieza = !limpieza;
            }
        }
    }
}
