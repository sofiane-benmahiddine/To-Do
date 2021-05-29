package com.opensource.todo.task;

import com.opensource.todo.user.models.User;
import com.opensource.todo.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public List<Task> getUserTasks() {
        User currentUser = getCurrentUser();
        return taskRepository.findAllByUser(currentUser);
    }

    public Optional<Task> getUserTaskById(String id) {
        User currentUser = getCurrentUser();
        return taskRepository.findByUserAndId(currentUser, id);
    }

    public Task addTask(TaskDto taskDto) {
        User currentUser = getCurrentUser();
        Task newTask = taskDto.toTask(currentUser);
        return taskRepository.save(newTask);

    }

    public Optional<String> removeTask(String id) {
        Optional<Task> opTask = getUserTaskById(id);
        if (opTask.isPresent()) {
            taskRepository.deleteById(id);
            return Optional.of(id);
        }
        return Optional.empty();
    }

    private User getCurrentUser() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String username = principal instanceof UserDetails ? (((UserDetails) principal).getUsername()) : principal.toString();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("username '%s' not found", username)));
    }

    public Optional<Task> editTask(TaskDto taskDto, String id) {
        Optional<Task> opTask = getUserTaskById(id);
        if (opTask.isPresent()) {
            Task task = opTask.get();
            task.setTitle(taskDto.getTitle());
            task.setStatus(taskDto.isStatus());
            Task newTask = taskRepository.save(task);
            return Optional.of(newTask);
        }
        return Optional.empty();
    }
}
