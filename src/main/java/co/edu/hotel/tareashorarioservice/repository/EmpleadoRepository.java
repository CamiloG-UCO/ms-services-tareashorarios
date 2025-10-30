package co.edu.hotel.tareashorarioservice.repository;

import co.edu.hotel.tareashorarioservice.domain.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByNombreCompleto(String nombreCompleto);
}
