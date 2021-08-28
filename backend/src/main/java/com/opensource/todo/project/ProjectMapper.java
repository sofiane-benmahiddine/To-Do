package com.opensource.todo.project;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {
    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(target = "name", source = "projectName")
    ProjectDto projectToProjectDto(Project project);

    @Mapping(target = "projectName", source = "name")
    Project projectDtoToProject(ProjectDto project);

    @Mapping(target = "name", source = "projectName")
    @Mapping(target = "endDate", source = "targetEndDate")
    ProjectForm projectToProjectForm(Project project);

    @Mapping(target = "projectName", source = "name")
    @Mapping(target = "targetEndDate", source = "endDate")
    @Mapping(target = "actualEndDate", source = "actualEndDate")
    Project projectFormToProject(ProjectForm projectForm);
}
