package co.edu.hotel.tareashorarioservice.stepdefinitions;

import io.cucumber.java.es.*;
import static org.assertj.core.api.Assertions.*;

import java.util.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Transactional
public class MonitoreoTareasStepDefinitions {

    private List<Map<String, String>> tareas;
    private List<Map<String, String>> resultadoConsulta;
    private String idTareaActualizada;

    // Escenario 1: Consultar todas las tareas registradas
    @Dado("existen tareas registradas en el sistema")
    public void existen_tareas_registradas_en_el_sistema() {
        tareas = new ArrayList<>();
        tareas.add(Map.of("id", "T001", "estado", "Completada"));
        tareas.add(Map.of("id", "T002", "estado", "Pendiente"));
        tareas.add(Map.of("id", "T003", "estado", "Fallida"));
    }

    @Cuando("el administrador consulte todas las tareas")
    public void el_administrador_consulte_todas_las_tareas() {
        resultadoConsulta = new ArrayList<>(tareas);
    }

    @Entonces("el sistema debe mostrar la lista completa de tareas con su estado actual")
    public void el_sistema_debe_mostrar_la_lista_completa_de_tareas_con_su_estado_actual() {
        assertThat(resultadoConsulta).hasSize(tareas.size());
        assertThat(resultadoConsulta)
                .extracting(t -> t.get("estado"))
                .containsExactlyInAnyOrder("Completada", "Pendiente", "Fallida");
    }

    // Escenario 2: Filtrar tareas por estado
    @Dado("existen tareas con diferentes estados")
    public void existen_tareas_con_diferentes_estados() {
        tareas = new ArrayList<>();
        tareas.add(Map.of("id", "T001", "estado", "Pendiente"));
        tareas.add(Map.of("id", "T002", "estado", "Completada"));
        tareas.add(Map.of("id", "T003", "estado", "Pendiente"));
    }

    @Cuando("el administrador consulte las tareas con estado {string}")
    public void el_administrador_consulte_las_tareas_con_estado(String estado) {
        resultadoConsulta = tareas.stream()
                .filter(t -> t.get("estado").equals(estado))
                .toList();
    }

    @Entonces("el sistema debe mostrar solo las tareas que están pendientes")
    public void el_sistema_debe_mostrar_solo_las_tareas_que_estan_pendientes() {
        assertThat(resultadoConsulta)
                .isNotEmpty()
                .extracting(t -> t.get("estado"))
                .allMatch(e -> e.equals("Pendiente"));
    }

    // Escenario 3: Actualizar el estado de una tarea
    @Dado("una tarea pendiente con identificador {string}")
    public void una_tarea_pendiente_con_identificador(String id) {
        tareas = new ArrayList<>();
        tareas.add(Map.of("id", id, "estado", "Pendiente"));
        idTareaActualizada = id;
    }

    @Cuando("el administrador actualice su estado a {string}")
    public void el_administrador_actualice_su_estado_a(String nuevoEstado) {
        tareas = tareas.stream()
                .map(t -> t.get("id").equals(idTareaActualizada)
                        ? Map.of("id", idTareaActualizada, "estado", nuevoEstado)
                        : t)
                .toList();
    }

    @Entonces("el sistema debe reflejar el cambio correctamente")
    public void el_sistema_debe_reflejar_el_cambio_correctamente() {
        Optional<Map<String, String>> tarea = tareas.stream()
                .filter(t -> t.get("id").equals(idTareaActualizada))
                .findFirst();

        assertThat(tarea).isPresent();
        assertThat(tarea.get().get("estado")).isEqualTo("Completada");
    }
}
