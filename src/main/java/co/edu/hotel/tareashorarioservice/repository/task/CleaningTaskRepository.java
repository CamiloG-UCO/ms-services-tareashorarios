package co.edu.hotel.tareashorarioservice.repository.task;

import co.edu.hotel.tareashorarioservice.domain.task.CleaningTask;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CleaningTaskRepository extends JpaRepository<CleaningTask, Long> {

    Optional<CleaningTask> findFirstByHotelNameIgnoreCaseAndRoomCodeIgnoreCaseOrderByCreatedAtDesc(
            String hotelName,
            String roomCode);

    Optional<CleaningTask> findByDescriptionIgnoreCase(String description);
}
