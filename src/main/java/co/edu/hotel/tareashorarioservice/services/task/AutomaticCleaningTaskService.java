package co.edu.hotel.tareashorarioservice.services.task;

import co.edu.hotel.tareashorarioservice.domain.task.CleaningTask;
import co.edu.hotel.tareashorarioservice.repository.task.CleaningTaskRepository;
import java.util.Locale;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutomaticCleaningTaskService {

    public static final String DEFAULT_ASSIGNEE = "Ana Torres";
    public static final int DEFAULT_ESTIMATED_MINUTES = 30;
    public static final String DEFAULT_STATUS = "Pendiente";
    private static final String TARGET_STATUS = "Disponible";

    private final CleaningTaskRepository cleaningTaskRepository;

    public AutomaticCleaningTaskService(CleaningTaskRepository cleaningTaskRepository) {
        this.cleaningTaskRepository = cleaningTaskRepository;
    }

    @Transactional
    public CleaningTask createTaskForCheckout(String hotelName, String roomCode, String newStatus) {
        String trimmedStatus = requireText(newStatus, "estado");
        if (!TARGET_STATUS.equalsIgnoreCase(trimmedStatus)) {
            throw new IllegalArgumentException("Solo se generan tareas cuando la habitación queda disponible tras el check-out");
        }

        String normalizedHotel = requireText(hotelName, "hotel");
        String normalizedRoom = requireText(roomCode, "habitación");

        Optional<CleaningTask> existing = cleaningTaskRepository
                .findFirstByHotelNameIgnoreCaseAndRoomCodeIgnoreCaseOrderByCreatedAtDesc(normalizedHotel, normalizedRoom)
                .filter(task -> DEFAULT_STATUS.equalsIgnoreCase(task.getStatus()));

        if (existing.isPresent()) {
            return existing.get();
        }

        CleaningTask toPersist = new CleaningTask(
                normalizedHotel,
                normalizedRoom,
                buildDescription(normalizedRoom),
                DEFAULT_ASSIGNEE,
                DEFAULT_ESTIMATED_MINUTES,
                DEFAULT_STATUS
        );

        return cleaningTaskRepository.save(toPersist);
    }

    @Transactional(readOnly = true)
    public Optional<CleaningTask> findLatestForRoom(String hotelName, String roomCode) {
        return cleaningTaskRepository
                .findFirstByHotelNameIgnoreCaseAndRoomCodeIgnoreCaseOrderByCreatedAtDesc(
                        requireText(hotelName, "hotel"),
                        requireText(roomCode, "habitación"));
    }

    private String buildDescription(String roomCode) {
        return String.format(Locale.getDefault(), "Limpieza habitación %s", roomCode);
    }

    private String requireText(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El campo " + fieldName + " es obligatorio");
        }
        return value.trim();
    }
}
