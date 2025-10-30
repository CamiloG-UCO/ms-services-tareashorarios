package co.edu.hotel.tareashorarioservice.services;

import co.edu.hotel.tareashorarioservice.domain.TaskDomain;
import co.edu.hotel.tareashorarioservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TaskMonitoringService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskMonitoringService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskDomain> monitorTasks() {
        return taskRepository.findAll();
    }

    public List<TaskDomain> getTasksByStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe especificar un estado de tarea válido.");
        }
        return taskRepository.findByStatus(status);
    }

    public TaskDomain updateTaskStatus(UUID taskId, String newStatus) {
        TaskDomain task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        task.setStatus(newStatus);

        if ("Completada".equalsIgnoreCase(newStatus)) {
            task.setEndTime(new Date());
        }

        return taskRepository.save(task);
    }

    public TaskDomain saveTask(TaskDomain task) {
        return taskRepository.save(task);
    }
}
