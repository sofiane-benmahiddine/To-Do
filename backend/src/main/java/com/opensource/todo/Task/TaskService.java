package com.opensource.todo.Task;

import com.opensource.todo.security.models.User;
import com.opensource.todo.security.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    public final TaskRepository taskRepository;
    public final UserRepository userRepository;

    public List<Task> getAllUserTasks() {
        String currentUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(currentUsername);
        if (user.isPresent()) {
            return taskRepository.findAllByUserId(user.get().getId());
        }
        return Collections.emptyList();
    }

    public Optional<Task> getUserTask(Long id) {
        String currentUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> opUser = userRepository.findByUsername(currentUsername);
        Optional<Task> opTask = taskRepository.findById(id);
        if (opTask.isPresent() && opUser.isPresent() && opTask.get().getUserId().equals(opUser.get().getId())) {
            return opTask;
        }
        return Optional.empty();
    }

    public Optional<Task> saveTask(TaskDto taskDto) {
        String currentUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(currentUsername);
        if (user.isPresent()) {
            Task task = taskDto.toTask(user.get());
            return Optional.of(taskRepository.save(task));
        }
        return Optional.empty();
    }

    public Optional<Task> updateTask(Long id, TaskDto taskDto) {
        Optional<Task> opTask = taskRepository.findById(id);
        String currentUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> opUser = userRepository.findByUsername(currentUsername);
        if (opTask.isPresent() && opUser.isPresent() && opTask.get().getUserId().equals(opUser.get().getId())) {
            Task newTask = taskDto.toTask(opUser.get());
            newTask.setId(opTask.get().getId());
            return Optional.of(taskRepository.save(newTask));
        }
        return Optional.empty();
    }

    public Long deleteTask(Long id) {
        Optional<Task> opTask = taskRepository.findById(id);
        String currentUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findByUsername(currentUsername);
        if (opTask.isPresent() && user.isPresent() && opTask.get().getUserId().equals(user.get().getId())) {
            taskRepository.deleteById(id);
            return id;
        }
        return 0L;
    }
}
