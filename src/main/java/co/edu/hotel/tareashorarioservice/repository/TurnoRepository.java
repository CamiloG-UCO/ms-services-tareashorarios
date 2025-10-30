package co.edu.hotel.tareashorarioservice.repository;

import co.edu.hotel.tareashorarioservice.domain.Turno;
import co.edu.hotel.tareashorarioservice.domain.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TurnoRepository extends JpaRepository<Turno, Long> {
    List<Turno> findByEmpleadoAndTurnoInicioBetween(Empleado empleado, LocalDate inicio, LocalDate fin);
}
