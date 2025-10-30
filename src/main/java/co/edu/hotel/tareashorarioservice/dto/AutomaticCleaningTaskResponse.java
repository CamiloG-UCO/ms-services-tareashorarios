package co.edu.hotel.tareashorarioservice.dto;

import co.edu.hotel.tareashorarioservice.domain.task.CleaningTask;
import java.time.LocalDateTime;

public class AutomaticCleaningTaskResponse {

    private boolean success;
    private String message;
    private Long taskId;
    private String hotelName;
    private String roomCode;
    private String description;
    private String assignedTo;
    private Integer estimatedMinutes;
    private String status;
    private String createdAt;
    private String error;

    private AutomaticCleaningTaskResponse() {
    }

    private AutomaticCleaningTaskResponse(boolean success,
                                          String message,
                                          Long taskId,
                                          String hotelName,
                                          String roomCode,
                                          String description,
                                          String assignedTo,
                                          Integer estimatedMinutes,
                                          String status,
                                          String createdAt,
                                          String error) {
        this.success = success;
        this.message = message;
        this.taskId = taskId;
        this.hotelName = hotelName;
        this.roomCode = roomCode;
        this.description = description;
        this.assignedTo = assignedTo;
        this.estimatedMinutes = estimatedMinutes;
        this.status = status;
        this.createdAt = createdAt;
        this.error = error;
    }

    public static AutomaticCleaningTaskResponse success(CleaningTask task, String message) {
        return new AutomaticCleaningTaskResponse(
                true,
                message,
                task != null ? task.getId() : null,
                task != null ? task.getHotelName() : null,
                task != null ? task.getRoomCode() : null,
                task != null ? task.getDescription() : null,
                task != null ? task.getAssignedTo() : null,
                task != null ? task.getEstimatedMinutes() : null,
                task != null ? task.getStatus() : null,
                formatDate(task != null ? task.getCreatedAt() : null),
                null
        );
    }

    public static AutomaticCleaningTaskResponse failure(String error) {
        return new AutomaticCleaningTaskResponse(false, null, null, null, null, null, null, null, null, null, error);
    }

    private static String formatDate(LocalDateTime value) {
        return value != null ? value.toString() : null;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public String getDescription() {
        return description;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public Integer getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getError() {
        return error;
    }
}
