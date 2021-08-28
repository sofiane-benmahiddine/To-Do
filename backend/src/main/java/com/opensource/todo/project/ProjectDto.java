package com.opensource.todo.project;

import lombok.Data;

import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class ProjectDto {
    @Size(min = 3, max = 50)
    private String name;
    private LocalDate startDate;
    private LocalDate targetEndDate;
    private LocalDate actualEndDate;
}
