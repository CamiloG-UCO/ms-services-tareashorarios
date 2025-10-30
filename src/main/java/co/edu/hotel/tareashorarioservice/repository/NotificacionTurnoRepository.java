package co.edu.hotel.tareashorarioservice.repository;

import co.edu.hotel.tareashorarioservice.domain.NotificacionTurno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionTurnoRepository extends JpaRepository<NotificacionTurno, Long> {
}

