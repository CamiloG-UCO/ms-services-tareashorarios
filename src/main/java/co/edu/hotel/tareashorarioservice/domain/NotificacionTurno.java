package co.edu.hotel.tareashorarioservice.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "notificaciones_turnos")
public class NotificacionTurno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;
    private String mensaje;
}

