package com.opensource.todo.project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, String> {
    Boolean existsByProjectName(String projectName);

}
