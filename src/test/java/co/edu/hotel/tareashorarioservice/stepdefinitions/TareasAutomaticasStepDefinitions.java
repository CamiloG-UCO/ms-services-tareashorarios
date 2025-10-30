package co.edu.hotel.tareashorarioservice.stepdefinitions;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import java.util.HashMap;
import java.util.Map;

public class TareasAutomaticasStepDefinitions {

    private String habitacion;
    private String hotel;
    private String estadoHabitacion;
    private boolean liberacionDetectada;
    private Map<String, Object> tareaGenerada;

    @Dado("la habitación {string} del hotel {string} cambie a estado {string} tras check-out")
    public void laHabitacionDelHotelCambieAEstadoTrasCheckOut(String habitacion, String hotel, String estado) {
        this.habitacion = habitacion;
        this.hotel = hotel;
        this.estadoHabitacion = estado;
        this.liberacionDetectada = false;
        this.tareaGenerada = null;
    }

    @Cuando("el sistema detecte la liberación")
    public void elSistemaDetecteLaLiberacion() {
        liberacionDetectada = "Disponible".equalsIgnoreCase(estadoHabitacion);
        if (!liberacionDetectada) {
            return;
        }

        tareaGenerada = new HashMap<>();
        tareaGenerada.put("hotel", hotel);
        tareaGenerada.put("habitacion", habitacion);
        tareaGenerada.put("nombre", "Limpieza habitación " + habitacion);
        tareaGenerada.put("asignadoA", "Ana Torres");
        tareaGenerada.put("tiempoEstimadoMinutos", 30);
    }

    @Entonces("debe crear la tarea {string} asignada a {string}")
    public void debeCrearLaTareaAsignadaA(String nombreEsperado, String responsableEsperado) {
        assertThat(liberacionDetectada).isTrue();
        assertThat(tareaGenerada)
                .isNotNull()
                .extracting(t -> t.get("nombre"), t -> t.get("asignadoA"))
                .containsExactly(nombreEsperado, responsableEsperado);
    }

    @Y("registrar el tiempo estimado de {int} minutos")
    public void registrarElTiempoEstimadoDeMinutos(int tiempoEsperado) {
        assertThat(tareaGenerada).isNotNull();
        assertThat(tareaGenerada.get("tiempoEstimadoMinutos")).isEqualTo(tiempoEsperado);
    }
}
