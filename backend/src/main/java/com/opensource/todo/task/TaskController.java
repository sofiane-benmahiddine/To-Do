package com.opensource.todo.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/task")
@Log4j2
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDto>> readAllTasks() {
        List<TaskDto> tasks = taskService.getUserTasks()
                .stream().map(TaskDto::taskToTaskDto).collect(Collectors.toList());
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> readTask(@PathVariable String id) {
        Optional<Task> opTask = taskService.getUserTaskById(id);
        if (opTask.isPresent()) {
            return new ResponseEntity<>(TaskDto.taskToTaskDto(opTask.get()), HttpStatus.OK);
        }
        return new ResponseEntity<>(String.format("Task with id '%s' not found", id), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody @Valid TaskDto taskDto) {
        taskService.addTask(taskDto);
        return new ResponseEntity<>("Task created successfully", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateTask(@RequestBody TaskDto taskDto, @PathVariable String id) {
        Optional<Task> opId = taskService.editTask(taskDto, id);
        if (opId.isPresent()) {
            return new ResponseEntity<>(String.format("Task with id '%s'updated successfully", id), HttpStatus.OK);
        }
        return new ResponseEntity<>(String.format("Task with id '%s' not found", id), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteTask(@PathVariable String id) {
        Optional<String> opId = taskService.removeTask(id);
        if (opId.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(String.format("Task with id '%s' not found", id), HttpStatus.NOT_FOUND);
    }
}