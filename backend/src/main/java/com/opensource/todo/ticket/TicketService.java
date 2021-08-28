package com.opensource.todo.ticket;

import com.opensource.todo.errors.exceptions.ForbiddenOperationException;
import com.opensource.todo.errors.exceptions.InvalidDateValueException;
import com.opensource.todo.errors.exceptions.TicketNotFoundException;
import com.opensource.todo.project.Project;
import com.opensource.todo.project.ProjectService;
import com.opensource.todo.ticket.models.Ticket;
import com.opensource.todo.ticket.models.TicketForm;
import com.opensource.todo.ticket.models.TicketMapper;
import com.opensource.todo.user.models.User;
import com.opensource.todo.user.services.UserService;
import com.opensource.todo.user.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ProjectService projectService;
    private final UserUtils userUtils;
    private final UserService userService;

    public Ticket getByTicketId(String id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException("Ticket with id " + id + " not found"));
        Project project = ticket.getRelatedProject();
        User user = userService.getUserById(userUtils.getCurrentUser().getId());
        isMemberOfProject(project, user);
        return ticket;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TESTER')")
    public Ticket addTicket(TicketForm ticketForm) {
        Ticket ticket = TicketMapper.INSTANCE.ticketFormToTicket(ticketForm);
        Project project = projectService.getProjectById(ticketForm.getRelatedProject());
        Long currentUserId = userUtils.getCurrentUser().getId();
        User user = userService.getUserById(currentUserId);
        isMemberOfProject(project, user);
        ticket.setIdentifiedBy(user);
        ticket.setRelatedProject(project);
        return ticketRepository.save(ticket);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_TESTER')")
    public void removeTicket(String id) {
        Ticket ticket = getByTicketId(id);
        Long ticketCreator = ticket.getCreatedBy();
        userUtils.userAccessFilter(ticketCreator);
        ticketRepository.deleteById(id);
    }

    private void isMemberOfProject(Project project, User user) {
        Set<User> projectMembers = project.getUsers();
        List<String> currentUserRoles = userUtils.getCurrentUserRoles();
        if (!projectMembers.contains(user) && !currentUserRoles.contains("ROLE_ADMIN"))
            throw new ForbiddenOperationException("Not member of this project");
    }

    public void isTicketValid(Ticket newTicket) {
        if (newTicket.getTargetResolutionDate() != null && newTicket.getIdentifiedDate() != null)
            if (newTicket.getTargetResolutionDate().isBefore(newTicket.getIdentifiedDate())) {
                throw new InvalidDateValueException("'Target Resolution Date' must be more recent than 'Identified Date'");
            }
        if (newTicket.getActualResolutionDate() != null && newTicket.getIdentifiedDate() != null)
            if (newTicket.getActualResolutionDate().isBefore(newTicket.getIdentifiedDate())) {
                throw new InvalidDateValueException("'Actual Resolution Date' must be more recent than 'Identified Date'");
            }
    }
}
