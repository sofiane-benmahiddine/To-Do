package com.opensource.todo.Task;

import com.opensource.todo.security.models.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TaskDto {
    @NotBlank(message = "title required")
    private String title;
    private boolean status = false;

    public Task toTask(User user) {
        return new Task(user.getId(), this.title, this.status);
    }


}
