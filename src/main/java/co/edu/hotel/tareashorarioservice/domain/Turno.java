package co.edu.hotel.tareashorarioservice.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "turnos")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;
    private LocalDate turnoInicio;
    private LocalDate turnoFinal;
    private String tarea;
    @ManyToOne
    @JoinColumn(name = "notificacion_id")
    private NotificacionTurno notificacion;

    public Turno(Empleado empleado, LocalDate turnoInicio, LocalDate turnoFinal, String tarea, NotificacionTurno notificacion) {
        this.empleado = empleado;
        this.turnoInicio = turnoInicio;
        this.turnoFinal = turnoFinal;
        this.tarea = tarea;
        this.notificacion = notificacion;
    }
}
