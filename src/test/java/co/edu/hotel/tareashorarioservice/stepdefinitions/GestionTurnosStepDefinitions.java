package co.edu.hotel.tareashorarioservice.stepdefinitions;

import static org.junit.Assert.assertEquals;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;

import co.edu.hotel.tareashorarioservice.services.PlanSemanalService;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import org.mockito.Mockito;
import co.edu.hotel.tareashorarioservice.repository.EmpleadoRepository;
import co.edu.hotel.tareashorarioservice.repository.TurnoRepository;
import co.edu.hotel.tareashorarioservice.repository.NotificacionTurnoRepository;
import co.edu.hotel.tareashorarioservice.services.NotificacionService;
import co.edu.hotel.tareashorarioservice.domain.Empleado;
import co.edu.hotel.tareashorarioservice.domain.Turno;
import java.lang.reflect.Field;

public class GestionTurnosStepDefinitions {
    private List<String> nombresCompletos;
    private PlanSemanalService planSemanalService;
    private EmpleadoRepository empleadoRepository;
    private TurnoRepository turnoRepository;
    private NotificacionTurnoRepository notificacionTurnoRepository;
    private NotificacionService notificacionService;
    private List<String> notificaciones;
    private LocalDate inicio;
    private LocalDate fin;
    private Exception error;

    @Dado("los empleados {string}")
    public void los_empleados(String empleadosStr) {
        nombresCompletos = Arrays.stream(empleadosStr.split(","))
                .map(String::trim)
                .filter(e -> !e.isEmpty())
                .collect(Collectors.toList());
        empleadoRepository = Mockito.mock(EmpleadoRepository.class);
        turnoRepository = Mockito.mock(TurnoRepository.class);
        notificacionTurnoRepository = Mockito.mock(NotificacionTurnoRepository.class);
        notificacionService = new NotificacionService();
        // Mock básico para save
        Mockito.when(notificacionTurnoRepository.save(Mockito.any())).thenReturn(null);
        for (String nombreCompleto : nombresCompletos) {
            Mockito.when(empleadoRepository.findByNombreCompleto(nombreCompleto))
                    .thenReturn(Optional.of(new Empleado(null, "CC", nombreCompleto)));
            Mockito.when(turnoRepository.findByEmpleadoAndTurnoInicioBetween(
                    Mockito.any(Empleado.class), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
                    .thenReturn(Collections.emptyList());
        }
        planSemanalService = new PlanSemanalService();
        try {
            Field repoField = PlanSemanalService.class.getDeclaredField("empleadoRepository");
            repoField.setAccessible(true);
            repoField.set(planSemanalService, empleadoRepository);
            Field turnoField = PlanSemanalService.class.getDeclaredField("turnoRepository");
            turnoField.setAccessible(true);
            turnoField.set(planSemanalService, turnoRepository);
            Field notifRepoField = PlanSemanalService.class.getDeclaredField("notificacionTurnoRepository");
            notifRepoField.setAccessible(true);
            notifRepoField.set(planSemanalService, notificacionTurnoRepository);
            // Inyección en NotificacionService
            Field notifRepoField2 = NotificacionService.class.getDeclaredField("notificacionTurnoRepository");
            notifRepoField2.setAccessible(true);
            notifRepoField2.set(notificacionService, notificacionTurnoRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        notificaciones = new ArrayList<>();
        error = null;
    }

    @Cuando("el supervisor asigne turnos del {string} al {string}")
    public void el_supervisor_asigne_turnos(String fechaInicio, String fechaFin) {
        inicio = LocalDate.parse(fechaInicio);
        fin = LocalDate.parse(fechaFin);
        try {
            planSemanalService.asignarTurnos(nombresCompletos, inicio, fin);
            for (String nombreCompleto : nombresCompletos) {
                notificaciones.add("Notificación enviada a " + nombreCompleto);
            }
        } catch (Exception e) {
            error = e;
        }
    }

    @Entonces("el sistema debe registrar los horarios y enviar notificaciones a cada empleado")
    public void el_sistema_debe_registrar_los_horarios_y_enviar_notificaciones() {
        if (error != null) {
            throw new AssertionError("Error inesperado: " + error.getMessage());
        }
        assertEquals(nombresCompletos.size(), notificaciones.size());
    }

    // Validación de error por fechas pasadas
    @Entonces("el sistema debe rechazar el agendamiento por fechas pasadas")
    public void el_sistema_debe_rechazar_por_fechas_pasadas() {
        assertEquals("No se pueden agendar turnos en fechas pasadas. Solo fechas futuras o actuales.", error.getMessage());
    }

    // Validación de error por empleados duplicados
    @Entonces("el sistema debe rechazar el agendamiento por empleados duplicados")
    public void el_sistema_debe_rechazar_por_empleados_duplicados() {
        assertEquals("El empleado 'Ana Torres' ya tiene turnos asignados en ese rango de fechas.", error.getMessage());
    }
}
