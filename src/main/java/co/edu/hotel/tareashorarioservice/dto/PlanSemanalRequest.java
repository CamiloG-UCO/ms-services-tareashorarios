package co.edu.hotel.tareashorarioservice.dto;

import java.util.List;

public class PlanSemanalRequest {
    private List<String> empleados;
    private String fechaInicio;
    private String fechaFin;

    public List<String> getEmpleados() {
        return empleados;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }
}

