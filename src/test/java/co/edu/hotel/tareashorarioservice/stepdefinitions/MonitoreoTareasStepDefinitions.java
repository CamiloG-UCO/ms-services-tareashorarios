package co.edu.hotel.tareashorarioservice.stepdefinitions;

import co.edu.hotel.tareashorarioservice.domain.TaskDomain;
import co.edu.hotel.tareashorarioservice.services.TaskMonitoringService;
import io.cucumber.java.es.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MonitoreoTareasStepDefinitions {

    @Autowired
    private TaskMonitoringService taskMonitoringService;

    private List<TaskDomain> resultado;
    private UUID idTareaActualizada;

    @Dado("existen tareas registradas en el sistema")
    public void existen_tareas_registradas_en_el_sistema() {
        taskMonitoringService.saveTask(new TaskDomain(null, "Limpieza habitación 101", "Carlos López", "Pendiente", new Date(), null));
        taskMonitoringService.saveTask(new TaskDomain(null, "Mantenimiento aire acondicionado", "Ana Ruiz", "Completada", new Date(), new Date()));
        taskMonitoringService.saveTask(new TaskDomain(null, "Revisión luces pasillo", "Pedro Gómez", "Fallida", new Date(), null));
    }

    @Cuando("el administrador consulte todas las tareas")
    public void el_administrador_consulte_todas_las_tareas() {
        resultado = taskMonitoringService.monitorTasks();
    }

    @Entonces("el sistema debe mostrar la lista completa de tareas con su estado actual")
    public void el_sistema_debe_mostrar_la_lista_completa_de_tareas_con_su_estado_actual() {
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.size()).isGreaterThanOrEqualTo(3);
    }

    @Dado("existen tareas con diferentes estados")
    public void existen_tareas_con_diferentes_estados() {
        taskMonitoringService.saveTask(new TaskDomain(null, "Limpieza general", "Laura Restrepo", "Pendiente", new Date(), null));
        taskMonitoringService.saveTask(new TaskDomain(null, "Cambio de bombillo", "José Pérez", "Completada", new Date(), new Date()));
    }

    @Cuando("el administrador consulte las tareas con estado {string}")
    public void el_administrador_consulte_las_tareas_con_estado(String estado) {
        resultado = taskMonitoringService.getTasksByStatus(estado);
    }

    @Entonces("el sistema debe mostrar solo las tareas que están pendientes")
    public void el_sistema_debe_mostrar_solo_las_tareas_que_estan_pendientes() {
        assertThat(resultado).isNotEmpty();
        assertThat(resultado)
                .extracting(TaskDomain::getStatus)
                .allMatch(e -> e.equals("Pendiente"));
    }

    @Dado("una tarea pendiente con identificador {string}")
    public void una_tarea_pendiente_con_identificador(String id) {
        TaskDomain tarea = taskMonitoringService.saveTask(
                new TaskDomain(null, "Limpieza baño", "Sofía Mejía", "Pendiente", new Date(), null)
        );
        idTareaActualizada = tarea.getTaskId();
    }

    @Cuando("el administrador actualice su estado a {string}")
    public void el_administrador_actualice_su_estado_a(String nuevoEstado) {
        taskMonitoringService.updateTaskStatus(idTareaActualizada, nuevoEstado);
    }

    @Entonces("el sistema debe reflejar el cambio correctamente")
    public void el_sistema_debe_reflejar_el_cambio_correctamente() {
        TaskDomain tarea = taskMonitoringService.monitorTasks().stream()
                .filter(t -> t.getTaskId().equals(idTareaActualizada))
                .findFirst()
                .orElseThrow();
        assertThat(tarea.getStatus()).isEqualTo("Completada");
    }
}
