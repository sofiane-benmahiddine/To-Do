package com.opensource.todo.project;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.opensource.todo.audit.Auditable;
import com.opensource.todo.user.models.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Project extends Auditable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String projectId;

    @NotBlank
    @Size(min = 3, max = 50)
    @Column(unique = true)
    private String projectName;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate targetEndDate;

    private LocalDate actualEndDate;

    @ManyToMany()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinTable(name = "user_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

    public Project(@NotBlank String projectName, @NotNull LocalDate startDate, @NotNull LocalDate targetEndDate) {
        this.projectName = projectName;
        this.startDate = startDate;
        this.targetEndDate = targetEndDate;
    }
}
