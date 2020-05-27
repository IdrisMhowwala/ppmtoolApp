package com.idris.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idris.ppmtool.domain.Backlog;
import com.idris.ppmtool.domain.Project;
import com.idris.ppmtool.exception.ProjectIdException;
import com.idris.ppmtool.exception.ProjectIdExceptionResponse;
import com.idris.ppmtool.repository.BacklogRepository;
import com.idris.ppmtool.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private BacklogRepository backlogRepository;

	public Project saveOrUpdate(Project project) {
		// while saving the roject backlog should be saved

		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

			// when project is create dfirst time backlog should be created with it.
			if (project.getId() == null) {
				Backlog backlog = new Backlog();
				project.setBacklog(backlog);
				backlog.setProject(project);
				backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			}

			// in case of update of backlog where it would not be null then same
			// projectIdentifier
			// should be set in backlog
			if (project.getId() != null) {
				project.setBacklog(
						backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
			}

			return projectRepository.save(project);
		} catch (Exception e) {
			throw new ProjectIdException("ProjectId Already Exists");
		}
	}

	public Project findProjectByIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if (project == null) {
			throw new ProjectIdException("Project Id: " + projectId.toUpperCase() + " does not exists");
		}
		return project;
	}

	public void deleteProjectByIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if (project == null) {
			throw new ProjectIdException("Can not delete project with id: " + projectId);
		}
		projectRepository.delete(project);
	}

	public Iterable<Project> findAllProjects() {
		return projectRepository.findAll();
	}

}
