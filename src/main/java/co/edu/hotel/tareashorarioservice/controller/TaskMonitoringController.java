package co.edu.hotel.tareashorarioservice.controller;

import co.edu.hotel.tareashorarioservice.domain.TaskDomain;
import co.edu.hotel.tareashorarioservice.services.TaskMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskMonitoringController {

    private final TaskMonitoringService taskMonitoringService;

    @Autowired
    public TaskMonitoringController(TaskMonitoringService taskMonitoringService) {
        this.taskMonitoringService = taskMonitoringService;
    }

    @GetMapping("/all")
    public List<TaskDomain> getAllTasks() {
        return taskMonitoringService.monitorTasks();
    }

    @GetMapping("/status/{status}")
    public List<TaskDomain> getTasksByStatus(@PathVariable String status) {
        return taskMonitoringService.getTasksByStatus(status);
    }

    @PutMapping("/update/{taskId}")
    public TaskDomain updateTaskStatus(@PathVariable UUID taskId, @RequestBody TaskDomain updatedTask) {
        return taskMonitoringService.updateTaskStatus(taskId, updatedTask.getStatus());
    }

    @PostMapping("/create")
    public TaskDomain createTask(@RequestBody TaskDomain newTask) {
        return taskMonitoringService.saveTask(newTask);
    }
}
