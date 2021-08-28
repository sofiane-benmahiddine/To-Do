package com.opensource.todo.ticket.models;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class TicketForm {
    private String ticketDescription;

    @NotBlank
    private String ticketSummary;

    @NotNull
    private LocalDate identifiedDate;

    @NotNull
    private String relatedProject;

    private Long assignedTo;

    private String priority;

    private LocalDate targetResolutionDate;

    private String progress;

    private LocalDate actualResolutionDate;

    private String resolutionSummary;
}
