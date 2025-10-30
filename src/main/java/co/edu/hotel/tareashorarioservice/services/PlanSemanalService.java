package co.edu.hotel.tareashorarioservice.services;

import co.edu.hotel.tareashorarioservice.domain.Empleado;
import co.edu.hotel.tareashorarioservice.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PlanSemanalService {
    @Autowired
    private EmpleadoRepository empleadoRepository;

    public void asignarTurnos(List<String> nombresCompletos, LocalDate inicio, LocalDate fin) {
        if (inicio == null || fin == null || !inicio.isBefore(fin)) {
            throw new IllegalArgumentException("Fechas de turno inválidas: inicio debe ser anterior a fin y no nulas.");
        }
        for (String nombreCompleto : nombresCompletos) {
            Optional<Empleado> empleadoOpt = empleadoRepository.findByNombreCompleto(nombreCompleto);
            if (empleadoOpt.isEmpty()) {
                throw new IllegalArgumentException("El empleado con nombre completo '" + nombreCompleto + "' no existe.");
            }
            Empleado empleado = empleadoOpt.get();
            System.out.println("Turnos asignados a " + empleado.getNombreCompleto() + " desde " + inicio + " hasta " + fin);
        }
    }
}
