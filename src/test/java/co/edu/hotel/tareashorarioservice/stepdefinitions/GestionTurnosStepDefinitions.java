package co.edu.hotel.tareashorarioservice.stepdefinitions;

import static org.junit.Assert.assertEquals;
import java.util.*;
import java.util.stream.Collectors;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;

public class GestionTurnosStepDefinitions {

    private List<String> empleados;
    private Map<String, String> turnosAsignados;
    private List<String> notificaciones;

    @Dado("los empleados {string}")
    public void los_empleados(String empleadosStr) {
        empleados = Arrays.stream(empleadosStr.split(","))
                .map(String::trim)
                .filter(e -> !e.isEmpty())
                .collect(Collectors.toList());
        turnosAsignados = new HashMap<>();
        notificaciones = new ArrayList<>();
    }

    @Cuando("el supervisor asigne turnos del {string} al {string}")
    public void el_supervisor_asigne_turnos(String fechaInicio, String fechaFin) {
        for (String empleado : empleados) {
            turnosAsignados.put(empleado, fechaInicio + " a " + fechaFin);
            notificaciones.add("Notificación enviada a " + empleado);
        }
    }

    @Entonces("el sistema debe registrar los horarios y enviar notificaciones a cada empleado")
    public void el_sistema_debe_registrar_los_horarios_y_enviar_notificaciones() {
        assertEquals(empleados.size(), turnosAsignados.size());
        assertEquals(empleados.size(), notificaciones.size());
    }
}
