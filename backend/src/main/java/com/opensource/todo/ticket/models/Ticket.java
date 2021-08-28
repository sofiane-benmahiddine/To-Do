package com.opensource.todo.ticket.models;

import com.opensource.todo.audit.Auditable;
import com.opensource.todo.project.Project;
import com.opensource.todo.user.models.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class Ticket extends Auditable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String ticketId;

    private String ticketDescription;

    @NotBlank
    private String ticketSummary;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "ticket_user_identified",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User identifiedBy;

    @NotNull
    private LocalDate identifiedDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "ticket_project",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Project relatedProject;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "ticket_user_assigned",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User assignedTo;

    @NotNull
    @Column(columnDefinition = "varchar(30) default 'OPEN'")
    private ETicketStatus status;

    @Column(columnDefinition = "varchar(30) default 'LOW'")
    private ETicketPriority priority;

    private LocalDate targetResolutionDate;

    private String progress;

    private LocalDate actualResolutionDate;

    private String resolutionSummary;

    @PrePersist
    private void onPrePersist() {
        if (this.priority == null)
            this.priority = ETicketPriority.LOW;
        if (this.status == null)
            this.status = ETicketStatus.OPEN;
    }

    @PreUpdate
    private void onPreUpdate() {
        if (this.actualResolutionDate != null)
            this.status = ETicketStatus.CLOSED;
    }
}
