package co.edu.hotel.tareashorarioservice.repository;

import co.edu.hotel.tareashorarioservice.domain.AsistenciaTurno;
import co.edu.hotel.tareashorarioservice.domain.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AsistenciaTurnoRepository extends JpaRepository<AsistenciaTurno, Long> {
    List<AsistenciaTurno> findByEmpleadoAndHoraEntradaAsignadaBetween(
            Empleado empleado,
            LocalDateTime inicio,
            LocalDateTime fin
    );
}