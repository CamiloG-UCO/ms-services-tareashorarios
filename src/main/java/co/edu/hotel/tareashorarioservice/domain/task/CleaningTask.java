package co.edu.hotel.tareashorarioservice.domain.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "cleaning_tasks")
public class CleaningTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hotel_name", nullable = false)
    private String hotelName;

    @Column(name = "room_code", nullable = false)
    private String roomCode;

    @Column(nullable = false)
    private String description;

    @Column(name = "assigned_to", nullable = false)
    private String assignedTo;

    @Column(name = "estimated_minutes", nullable = false)
    private Integer estimatedMinutes;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    protected CleaningTask() {
        // JPA only
    }

    public CleaningTask(String hotelName,
                        String roomCode,
                        String description,
                        String assignedTo,
                        Integer estimatedMinutes,
                        String status) {
        this.hotelName = Objects.requireNonNull(hotelName, "hotelName");
        this.roomCode = Objects.requireNonNull(roomCode, "roomCode");
        this.description = Objects.requireNonNull(description, "description");
        this.assignedTo = Objects.requireNonNull(assignedTo, "assignedTo");
        this.estimatedMinutes = Objects.requireNonNull(estimatedMinutes, "estimatedMinutes");
        this.status = Objects.requireNonNull(status, "status");
    }

    @PrePersist
    public void prePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
