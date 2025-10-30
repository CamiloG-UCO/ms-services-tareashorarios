package co.edu.hotel.tareashorarioservice.controller;

import co.edu.hotel.tareashorarioservice.domain.task.CleaningTask;
import co.edu.hotel.tareashorarioservice.dto.AutomaticCleaningTaskRequest;
import co.edu.hotel.tareashorarioservice.dto.AutomaticCleaningTaskResponse;
import co.edu.hotel.tareashorarioservice.services.task.AutomaticCleaningTaskService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/automatic-cleaning-tasks")
public class AutomaticCleaningTaskController {

    private final AutomaticCleaningTaskService automaticCleaningTaskService;

    public AutomaticCleaningTaskController(AutomaticCleaningTaskService automaticCleaningTaskService) {
        this.automaticCleaningTaskService = automaticCleaningTaskService;
    }

    @PostMapping
    public ResponseEntity<AutomaticCleaningTaskResponse> createAutomaticTask(@RequestBody AutomaticCleaningTaskRequest request) {
        try {
            Optional<CleaningTask> pendingTask = automaticCleaningTaskService.findLatestForRoom(
                    request.getHotelName(),
                    request.getRoomCode())
                    .filter(task -> AutomaticCleaningTaskService.DEFAULT_STATUS.equalsIgnoreCase(task.getStatus()));

            CleaningTask task = automaticCleaningTaskService.createTaskForCheckout(
                    request.getHotelName(),
                    request.getRoomCode(),
                    request.getNewStatus());

            String message = pendingTask.isPresent()
                    ? "Ya existe una tarea pendiente para la habitación, se retorna la más reciente."
                    : "Se creó una nueva tarea automática de limpieza.";
            return ResponseEntity.ok(AutomaticCleaningTaskResponse.success(task, message));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(AutomaticCleaningTaskResponse.failure(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(AutomaticCleaningTaskResponse.failure("No fue posible generar la tarea automática"));
        }
    }
}
