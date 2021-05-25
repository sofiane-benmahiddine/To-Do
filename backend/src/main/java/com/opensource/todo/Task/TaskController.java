package com.opensource.todo.Task;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(taskService.getAllUserTasks(), HttpStatus.OK);
    }

    @GetMapping("{taskId}")
    public ResponseEntity<?> getTask(@PathVariable Long taskId) {
        Optional<Task> opTask = taskService.getUserTask(taskId);
        if (opTask.isPresent()) {
            return new ResponseEntity<>(opTask.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody @Valid TaskDto taskDto) {
        Optional<Task> task = taskService.saveTask(taskDto);
        if (task.isPresent()) {
            return new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Task creation failed, User not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping("{taskId}")
    public ResponseEntity<?> put(@RequestBody @Valid TaskDto taskDto, @PathVariable Long taskId) {
        Optional<Task> opTask = taskService.updateTask(taskId, taskDto);
        if (opTask.isPresent()) {
            return new ResponseEntity<>("Task updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Task updating failed", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("{taskId}")
    public ResponseEntity<?> delete(@PathVariable Long taskId) {
        Long deleted = taskService.deleteTask(taskId);
        if (deleted != 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Task deletion failed", HttpStatus.NOT_FOUND);
    }
}
