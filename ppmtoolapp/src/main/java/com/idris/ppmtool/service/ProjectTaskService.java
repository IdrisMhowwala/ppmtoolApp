package com.idris.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idris.ppmtool.domain.Backlog;
import com.idris.ppmtool.domain.ProjectTask;
import com.idris.ppmtool.repository.BacklogRepository;
import com.idris.ppmtool.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		// Exception handling: in case project is not available

		// ProjectTAsk should be added to a specific Project , project != null, backlog
		// exist
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

		projectTask.setBacklog(backlog);

		Integer backlogSequence = backlog.getPTSequence();

		backlogSequence++;

		projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);

		projectTask.setProjectIdentifer(projectIdentifier);

		// setting default priority and status

		if (projectTask.getPriority() == null) {
			projectTask.setPriority(3);

		}
		if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
			projectTask.setStatus("TODO");
		}

		return projectTaskRepository.save(projectTask);

	}

}
