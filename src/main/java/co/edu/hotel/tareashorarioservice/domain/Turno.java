package co.edu.hotel.tareashorarioservice.domain;

import java.time.LocalDate;

public class Turno {
    private final Empleado empleado;
    private final LocalDate fecha;
    private final String tarea;

    public Turno(Empleado empleado, LocalDate fecha, String tarea) {
        this.empleado = empleado;
        this.fecha = fecha;
        this.tarea = tarea;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getTarea() {
        return tarea;
    }
}

