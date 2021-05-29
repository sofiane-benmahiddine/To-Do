package com.opensource.todo.task;

import com.opensource.todo.user.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
public class TaskDto {
    private String id;
    @NotBlank
    private String title;
    private boolean status;
    private Date createdAt;

    Task toTask(User user) {
        return new Task(user, this.title, this.status);
    }

    static TaskDto taskToTaskDto(Task task) {
        return new TaskDto(task.getId(), task.getTitle(), task.isStatus(), task.getCreatedAt());
    }
}
