package me.wreis.todolist.task;

import jakarta.servlet.http.HttpServletRequest;
import me.wreis.todolist.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.created;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private ITaskRepository taskRepository;

    public TaskController(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @PostMapping("/")
    public ResponseEntity Create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        taskModel.setUserId((UUID) request.getAttribute("userId"));
        taskModel.setDone(false);
        if(LocalDateTime.now().isAfter(taskModel.getStartAt())){
            return ResponseEntity.badRequest().body("startAt must be after current date");
        }
        if (taskModel.getStartAt().isAfter(taskModel.getFinishAt())) {
            return ResponseEntity.badRequest().body("finishAt must be after startAt");
        }

        return ResponseEntity.created(null).body(this.taskRepository.save(taskModel));
    }
    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        var userId = request.getAttribute("userId");
        return this.taskRepository.findByUserId((UUID) userId);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID taskId, HttpServletRequest request) {
        var userId = request.getAttribute("userId");
        var task = this.taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        if (!task.getUserId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }
        Utils.copyNonNullProperties(taskModel, task);
        var taskUpdated = this.taskRepository.save(task);
        return ResponseEntity.ok(taskUpdated);
    }
}
