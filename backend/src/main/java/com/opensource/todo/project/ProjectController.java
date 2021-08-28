package com.opensource.todo.project;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public List<Project> readAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> readByProjectId(@PathVariable String id) {
        return new ResponseEntity<>(projectService.getProjectById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody @Valid ProjectForm projectForm) {
        Project project = ProjectMapper.INSTANCE.projectFormToProject(projectForm);
        return new ResponseEntity<>(projectService.addProject(project), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateProject(@RequestBody @Valid ProjectForm projectForm, @PathVariable String id) {
        Project project = ProjectMapper.INSTANCE.projectFormToProject(projectForm);
        return new ResponseEntity<>(projectService.editProject(id, project), HttpStatus.OK);
    }

    @DeleteMapping("{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable String projectId) {
        projectService.removeProject(projectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestParam String projectId, @RequestParam Long userId) {
        projectService.manageProjectUsers(userId, projectId, Operation.ADD);
        return new ResponseEntity<>("User added successfully to the project", HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> removeUser(@RequestParam String projectId, @RequestParam Long userId) {
        projectService.manageProjectUsers(userId, projectId, Operation.REMOVE);
        return new ResponseEntity<>("User deleted successfully from the project", HttpStatus.OK);
    }
}
