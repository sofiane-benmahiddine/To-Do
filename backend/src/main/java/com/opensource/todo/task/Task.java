package com.opensource.todo.task;

import com.opensource.todo.user.models.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Task {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @OneToOne
    @JoinTable(name = "user_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;
    private String title;
    private boolean status;
    private Date createdAt;

    public Task(User user, String title, boolean status) {
        this.user = user;
        this.title = title;
        this.status = status;
    }

    @PrePersist
    void createdAt() {
        this.createdAt = new Date();
    }
}
