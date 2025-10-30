package co.edu.hotel.tareashorarioservice.services;

import co.edu.hotel.tareashorarioservice.domain.AsistenciaTurno;
import co.edu.hotel.tareashorarioservice.domain.Empleado;
import co.edu.hotel.tareashorarioservice.repository.AsistenciaTurnoRepository;
import co.edu.hotel.tareashorarioservice.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AsistenciaTurnoService {

    @Autowired
    private AsistenciaTurnoRepository asistenciaTurnoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public AsistenciaTurno registrarEntrada(Long empleadoId) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime inicioTurno = ahora.truncatedTo(ChronoUnit.DAYS).withHour(8);
        LocalDateTime finTurno = ahora.truncatedTo(ChronoUnit.DAYS).withHour(16);

        AsistenciaTurno asistencia = new AsistenciaTurno();
        asistencia.setEmpleado(empleado);
        asistencia.setHoraEntradaAsignada(inicioTurno);
        asistencia.setHoraSalidaAsignada(finTurno);
        asistencia.setHoraEntradaReal(ahora);

        return asistenciaTurnoRepository.save(asistencia);
    }

    public AsistenciaTurno registrarSalida(Long empleadoId) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime inicioDia = ahora.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime finDia = inicioDia.plusDays(1);

        List<AsistenciaTurno> asistencias = asistenciaTurnoRepository
                .findByEmpleadoAndHoraEntradaAsignadaBetween(empleado, inicioDia, finDia);

        if (asistencias.isEmpty()) {
            throw new RuntimeException("No se encontró una entrada registrada para hoy");
        }

        // Toma el último registro del día (por si hay más de uno)
        AsistenciaTurno asistencia = asistencias.get(asistencias.size() - 1);
        asistencia.setHoraSalidaReal(ahora);

        return asistenciaTurnoRepository.save(asistencia);
    }
}
