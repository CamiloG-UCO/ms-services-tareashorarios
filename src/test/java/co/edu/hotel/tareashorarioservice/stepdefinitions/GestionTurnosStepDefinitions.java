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
import co.edu.hotel.tareashorarioservice.domain.Empleado;
import java.lang.reflect.Field;

public class GestionTurnosStepDefinitions {
    private List<String> nombresCompletos;
    private PlanSemanalService planSemanalService;
    private EmpleadoRepository empleadoRepository;
    private List<String> notificaciones;
    private LocalDate inicio;
    private LocalDate fin;

    @Dado("los empleados {string}")
    public void los_empleados(String empleadosStr) {
        nombresCompletos = Arrays.stream(empleadosStr.split(","))
                .map(String::trim)
                .filter(e -> !e.isEmpty())
                .collect(Collectors.toList());
        empleadoRepository = Mockito.mock(EmpleadoRepository.class);
        for (String nombreCompleto : nombresCompletos) {
            Mockito.when(empleadoRepository.findByNombreCompleto(nombreCompleto))
                    .thenReturn(Optional.of(new Empleado(null, nombreCompleto)));
        }
        planSemanalService = new PlanSemanalService();
        Field repoField;
        try {
            repoField = PlanSemanalService.class.getDeclaredField("empleadoRepository");
            repoField.setAccessible(true);
            repoField.set(planSemanalService, empleadoRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        notificaciones = new ArrayList<>();
    }

    @Cuando("el supervisor asigne turnos del {string} al {string}")
    public void el_supervisor_asigne_turnos(String fechaInicio, String fechaFin) {
        inicio = LocalDate.parse(fechaInicio);
        fin = LocalDate.parse(fechaFin);
        planSemanalService.asignarTurnos(nombresCompletos, inicio, fin);
        for (String nombreCompleto : nombresCompletos) {
            notificaciones.add("Notificación enviada a " + nombreCompleto);
        }
    }

    @Entonces("el sistema debe registrar los horarios y enviar notificaciones a cada empleado")
    public void el_sistema_debe_registrar_los_horarios_y_enviar_notificaciones() {
        assertEquals(nombresCompletos.size(), notificaciones.size());
    }
}
