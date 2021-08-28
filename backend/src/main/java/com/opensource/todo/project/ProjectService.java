package com.opensource.todo.project;

import com.google.common.collect.ImmutableList;
import com.opensource.todo.errors.exceptions.InvalidDateValueException;
import com.opensource.todo.errors.exceptions.ProjectAlreadyExistsException;
import com.opensource.todo.errors.exceptions.ProjectNotFoundException;
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
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final UserUtils userUtils;

    public List<Project> getAllProjects() {
        return ImmutableList.copyOf(projectRepository.findAll());
    }

    public Project getProjectById(String id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project with id " + id + " not found"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public Project addProject(Project project) {
        isProjectValid(project);
        return projectRepository.save(project);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    public Project editProject(String id, Project newProject) {
        Project project = getProjectById(id);
        Long projectManagerId = project.getCreatedBy();
        userUtils.userAccessFilter(projectManagerId);
        isProjectValid(newProject, project);
        if (newProject.getProjectName() != null)
            project.setProjectName(newProject.getProjectName());
        if (newProject.getStartDate() != null)
            project.setStartDate(newProject.getStartDate());
        if (newProject.getTargetEndDate() != null)
            project.setTargetEndDate(newProject.getTargetEndDate());
        if (newProject.getActualEndDate() != null)
            project.setActualEndDate(newProject.getActualEndDate());
        return projectRepository.save(project);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public void removeProject(String id) {
        Project project = getProjectById(id);
        Long projectManagerId = project.getCreatedBy();
        userUtils.userAccessFilter(projectManagerId);
        projectRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public void manageProjectUsers(Long userId, String projectId, Operation operation) {
        Project project = getProjectById(projectId);
        Long projectManagerId = project.getCreatedBy();
        userUtils.userAccessFilter(projectManagerId);
        User user = userService.getUserById(userId);
        Set<User> projectUsers = project.getUsers();
        if (operation == Operation.ADD) {
            projectUsers.add(user);
        } else {
            projectUsers.remove(user);
        }
        project.setUsers(projectUsers);
        projectRepository.save(project);
    }

    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public void removeUserFromProject(Long UserId, String ProjectId) {

    }

    public void isProjectValid(Project project) {
        if (projectRepository.existsByProjectName(project.getProjectName())) {
            throw new ProjectAlreadyExistsException("project with the same name exists already");
        }
        if (project.getTargetEndDate() != null && project.getStartDate() != null)
            if (project.getTargetEndDate().isBefore(project.getStartDate())) {
                throw new InvalidDateValueException("'End date' must be more recent than 'Start date'");
            }
    }

    public void isProjectValid(Project newProject, Project oldProject) {
        if (projectRepository.existsByProjectName(newProject.getProjectName())
                && !oldProject.getProjectName().equals(newProject.getProjectName())) {
            throw new ProjectAlreadyExistsException("project with the same name exists already");
        }
        if (newProject.getTargetEndDate() != null && newProject.getStartDate() != null)
            if (newProject.getTargetEndDate().isBefore(newProject.getStartDate())) {
                throw new InvalidDateValueException("'End date' must be more recent than 'Start date'");
            }
        if (newProject.getActualEndDate() != null && newProject.getStartDate() != null)
            if (newProject.getActualEndDate().isBefore(newProject.getStartDate())) {
                throw new InvalidDateValueException("'Actual end date' must be more recent than 'Start date'");
            }
    }
}
