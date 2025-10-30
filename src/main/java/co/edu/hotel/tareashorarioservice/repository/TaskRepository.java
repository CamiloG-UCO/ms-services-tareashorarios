package co.edu.hotel.tareashorarioservice.repository;

import co.edu.hotel.tareashorarioservice.domain.TaskDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskDomain, UUID> {
    List<TaskDomain> findByStatus(String status);
}
