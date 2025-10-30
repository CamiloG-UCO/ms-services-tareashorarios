package co.edu.hotel.tareashorarioservice.stepdefinitions;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.hotel.tareashorarioservice.domain.task.CleaningTask;
import co.edu.hotel.tareashorarioservice.repository.task.CleaningTaskRepository;
import co.edu.hotel.tareashorarioservice.services.task.AutomaticCleaningTaskService;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextBeforeModesTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@CucumberContextConfiguration
@SpringBootTest
@TestExecutionListeners(value = {
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextBeforeModesTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class
}, mergeMode = TestExecutionListeners.MergeMode.REPLACE_DEFAULTS)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "spring.jpa.show-sql=false",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
@Transactional
public class TareasAutomaticasStepDefinitions {

    @Autowired
    private AutomaticCleaningTaskService automaticCleaningTaskService;

    @Autowired
    private CleaningTaskRepository cleaningTaskRepository;

    private String habitacion;
    private String hotel;
    private String estadoHabitacion;
    private CleaningTask tareaGenerada;

    @Dado("la habitación {string} del hotel {string} cambie a estado {string} tras check-out")
    public void laHabitacionDelHotelCambieAEstadoTrasCheckOut(String habitacion, String hotel, String estado) {
        this.habitacion = habitacion;
        this.hotel = hotel;
        this.estadoHabitacion = estado;
        this.tareaGenerada = null;
        cleaningTaskRepository.deleteAll();
    }

    @Cuando("el sistema detecte la liberación")
    public void elSistemaDetecteLaLiberacion() {
        tareaGenerada = automaticCleaningTaskService
                .createTaskForCheckout(hotel, habitacion, estadoHabitacion);
    }

    @Entonces("debe crear la tarea {string} asignada a {string}")
    public void debeCrearLaTareaAsignadaA(String nombreEsperado, String responsableEsperado) {
        assertThat(tareaGenerada).as("La tarea debe generarse").isNotNull();
        assertThat(tareaGenerada.getDescription()).isEqualTo(nombreEsperado);
        assertThat(tareaGenerada.getAssignedTo()).isEqualTo(responsableEsperado);

        CleaningTask persisted = cleaningTaskRepository.findByDescriptionIgnoreCase(nombreEsperado)
                .orElseThrow(() -> new AssertionError("No se encontró la tarea persistida"));

        assertThat(persisted.getAssignedTo()).isEqualTo(responsableEsperado);
        assertThat(persisted.getHotelName()).isEqualTo(hotel);
        assertThat(persisted.getRoomCode()).isEqualTo(habitacion);
    }

    @Y("registrar el tiempo estimado de {int} minutos")
    public void registrarElTiempoEstimadoDeMinutos(int tiempoEsperado) {
        assertThat(tareaGenerada).isNotNull();
        assertThat(tareaGenerada.getEstimatedMinutes()).isEqualTo(tiempoEsperado);

        CleaningTask persisted = cleaningTaskRepository.findByDescriptionIgnoreCase(tareaGenerada.getDescription())
                .orElseThrow(() -> new AssertionError("No se encontró la tarea persistida"));
        assertThat(persisted.getEstimatedMinutes()).isEqualTo(tiempoEsperado);
    }
}
