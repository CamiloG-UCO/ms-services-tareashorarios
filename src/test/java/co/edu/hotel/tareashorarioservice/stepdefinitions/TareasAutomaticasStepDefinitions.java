package co.edu.hotel.tareashorarioservice.stepdefinitions;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.hotel.tareashorarioservice.domain.task.CleaningTask;
import co.edu.hotel.tareashorarioservice.repository.task.CleaningTaskRepository;
import co.edu.hotel.tareashorarioservice.services.task.AutomaticCleaningTaskService;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import java.util.Optional;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class TareasAutomaticasStepDefinitions {

    private AutomaticCleaningTaskService automaticCleaningTaskService;
    private CleaningTaskRepository cleaningTaskRepository;
    private ArgumentCaptor<CleaningTask> cleaningTaskCaptor;

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
        cleaningTaskRepository = Mockito.mock(CleaningTaskRepository.class);
        automaticCleaningTaskService = new AutomaticCleaningTaskService(cleaningTaskRepository);
        cleaningTaskCaptor = ArgumentCaptor.forClass(CleaningTask.class);

        Mockito.when(cleaningTaskRepository
                        .findFirstByHotelNameIgnoreCaseAndRoomCodeIgnoreCaseOrderByCreatedAtDesc(
                                Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Optional.empty());
        Mockito.when(cleaningTaskRepository.save(cleaningTaskCaptor.capture()))
                .thenAnswer(invocation -> invocation.getArgument(0));
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
        Mockito.verify(cleaningTaskRepository).save(Mockito.any(CleaningTask.class));

        CleaningTask persisted = cleaningTaskCaptor.getValue();
        assertThat(persisted.getAssignedTo()).isEqualTo(responsableEsperado);
        assertThat(persisted.getHotelName()).isEqualTo(hotel);
        assertThat(persisted.getRoomCode()).isEqualTo(habitacion);
    }

    @Y("registrar el tiempo estimado de {int} minutos")
    public void registrarElTiempoEstimadoDeMinutos(int tiempoEsperado) {
        assertThat(tareaGenerada).isNotNull();
        assertThat(tareaGenerada.getEstimatedMinutes()).isEqualTo(tiempoEsperado);
        CleaningTask persisted = cleaningTaskCaptor.getValue();
        assertThat(persisted.getEstimatedMinutes()).isEqualTo(tiempoEsperado);
    }
}
