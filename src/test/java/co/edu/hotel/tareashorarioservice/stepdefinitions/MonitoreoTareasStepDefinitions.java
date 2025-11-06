package co.edu.hotel.tareashorarioservice.stepdefinitions;

import co.edu.hotel.tareashorarioservice.domain.TaskDomain;
import co.edu.hotel.tareashorarioservice.repository.TaskRepository;
import co.edu.hotel.tareashorarioservice.services.TaskMonitoringService;
import io.cucumber.java.es.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class MonitoreoTareasStepDefinitions {

    private TaskMonitoringService taskMonitoringService;
    private TaskRepository taskRepository;
    private ArgumentCaptor<TaskDomain> taskCaptor;

    private List<TaskDomain> tareasRegistradas;
    private List<TaskDomain> resultado;
    private TaskDomain tareaActualizada;

    @Dado("existen tareas registradas en el sistema")
    public void existenTareasRegistradasEnElSistema() {
        // Mock del repositorio
        taskRepository = Mockito.mock(TaskRepository.class);
        taskMonitoringService = new TaskMonitoringService(taskRepository);
        taskCaptor = ArgumentCaptor.forClass(TaskDomain.class);

        tareasRegistradas = Arrays.asList(
                new TaskDomain(UUID.randomUUID(), "Limpieza habitación 101", "Carlos López", "Pendiente", new Date(), null),
                new TaskDomain(UUID.randomUUID(), "Mantenimiento aire acondicionado", "Ana Ruiz", "Completada", new Date(), new Date()),
                new TaskDomain(UUID.randomUUID(), "Revisión luces pasillo", "Pedro Gómez", "Fallida", new Date(), null)
        );

        Mockito.when(taskRepository.findAll()).thenReturn(tareasRegistradas);
    }

    @Cuando("el administrador consulte todas las tareas")
    public void elAdministradorConsulteTodasLasTareas() {
        resultado = taskMonitoringService.monitorTasks();
    }

    @Entonces("el sistema debe mostrar la lista completa de tareas con su estado actual")
    public void elSistemaDebeMostrarLaListaCompletaDeTareasConSuEstadoActual() {
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isEqualTo(tareasRegistradas.size());
        Mockito.verify(taskRepository).findAll();
    }

    @Dado("existen tareas con diferentes estados")
    public void existenTareasConDiferentesEstados() {
        taskRepository = Mockito.mock(TaskRepository.class);
        taskMonitoringService = new TaskMonitoringService(taskRepository);

        List<TaskDomain> pendientes = List.of(
                new TaskDomain(UUID.randomUUID(), "Limpieza general", "Laura Restrepo", "Pendiente", new Date(), null)
        );

        Mockito.when(taskRepository.findByStatus("Pendiente")).thenReturn(pendientes);
    }

    @Cuando("el administrador consulte las tareas con estado {string}")
    public void elAdministradorConsulteLasTareasConEstado(String estado) {
        resultado = taskMonitoringService.getTasksByStatus(estado);
    }

    @Entonces("el sistema debe mostrar solo las tareas que están pendientes")
    public void elSistemaDebeMostrarSoloLasTareasQueEstanPendientes() {
        assertThat(resultado).isNotEmpty();
        assertThat(resultado)
                .extracting(TaskDomain::getStatus)
                .allMatch(e -> e.equals("Pendiente"));
        Mockito.verify(taskRepository).findByStatus("Pendiente");
    }

    @Dado("una tarea pendiente registrada")
    public void unaTareaPendienteRegistrada() {
        taskRepository = Mockito.mock(TaskRepository.class);
        taskMonitoringService = new TaskMonitoringService(taskRepository);
        taskCaptor = ArgumentCaptor.forClass(TaskDomain.class);

        UUID idTarea = UUID.randomUUID();
        TaskDomain tareaPendiente = new TaskDomain(idTarea, "Limpieza baño", "Sofía Mejía", "Pendiente", new Date(), null);

        Mockito.when(taskRepository.findById(idTarea)).thenReturn(Optional.of(tareaPendiente));
        Mockito.when(taskRepository.save(taskCaptor.capture())).thenAnswer(inv -> inv.getArgument(0));

        this.tareaActualizada = tareaPendiente;
    }

    @Cuando("el administrador actualice su estado a {string}")
    public void elAdministradorActualiceSuEstadoA(String nuevoEstado) {
        tareaActualizada = taskMonitoringService.updateTaskStatus(tareaActualizada.getTaskId(), nuevoEstado);
    }

    @Entonces("el sistema debe reflejar el cambio correctamente")
    public void elSistemaDebeReflejarElCambioCorrectamente() {
        assertThat(tareaActualizada).isNotNull();
        assertThat(tareaActualizada.getStatus()).isEqualTo("Completada");
        Mockito.verify(taskRepository).save(Mockito.any(TaskDomain.class));
    }
}
