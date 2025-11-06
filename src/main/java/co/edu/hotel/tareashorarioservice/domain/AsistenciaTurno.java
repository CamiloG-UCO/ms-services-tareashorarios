package co.edu.hotel.tareashorarioservice.domain;

import co.edu.hotel.tareashorarioservice.domain.Empleado;
import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "asistencia_turno")
public class AsistenciaTurno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    private LocalDateTime horaEntradaAsignada;
    private LocalDateTime horaSalidaAsignada;
    private LocalDateTime horaEntradaReal;
    private LocalDateTime horaSalidaReal;
    private long minutosRetraso;
    private long minutosTrabajados;

    @PrePersist
    @PreUpdate
    private void calcularDatos() {
        if (horaEntradaReal != null && horaEntradaAsignada != null) {
            minutosRetraso = Duration.between(horaEntradaAsignada, horaEntradaReal).toMinutes();
            if (minutosRetraso < 0) minutosRetraso = 0;
        }
        if (horaEntradaReal != null && horaSalidaReal != null) {
            minutosTrabajados = Duration.between(horaEntradaReal, horaSalidaReal).toMinutes();
        }
    }

    // Getters y setters
    public Long getId() { return id; }
    public Empleado getEmpleado() { return empleado; }
    public void setEmpleado(Empleado empleado) { this.empleado = empleado; }
    public LocalDateTime getHoraEntradaAsignada() { return horaEntradaAsignada; }
    public void setHoraEntradaAsignada(LocalDateTime horaEntradaAsignada) { this.horaEntradaAsignada = horaEntradaAsignada; }
    public LocalDateTime getHoraSalidaAsignada() { return horaSalidaAsignada; }
    public void setHoraSalidaAsignada(LocalDateTime horaSalidaAsignada) { this.horaSalidaAsignada = horaSalidaAsignada; }
    public LocalDateTime getHoraEntradaReal() { return horaEntradaReal; }
    public void setHoraEntradaReal(LocalDateTime horaEntradaReal) { this.horaEntradaReal = horaEntradaReal; }
    public LocalDateTime getHoraSalidaReal() { return horaSalidaReal; }
    public void setHoraSalidaReal(LocalDateTime horaSalidaReal) { this.horaSalidaReal = horaSalidaReal; }
    public long getMinutosRetraso() { return minutosRetraso; }
    public long getMinutosTrabajados() { return minutosTrabajados; }
}