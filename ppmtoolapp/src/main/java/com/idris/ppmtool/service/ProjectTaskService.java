package com.idris.ppmtool.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idris.ppmtool.domain.Backlog;
import com.idris.ppmtool.domain.Project;
import com.idris.ppmtool.domain.ProjectTask;
import com.idris.ppmtool.exception.ProjectNotFoundException;
import com.idris.ppmtool.repository.BacklogRepository;
import com.idris.ppmtool.repository.ProjectRepository;
import com.idris.ppmtool.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private ProjectRepository projectRepository;

	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {

		try {
			// Exception handling: in case project is not available

			// ProjectTAsk should be added to a specific Project , project != null, backlog exist
			Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);

			//set the backlog to project task
			projectTask.setBacklog(backlog);

			Integer backlogSequence = backlog.getPTSequence();

			backlogSequence++;

			projectTask.setProjectSequence(projectIdentifier + "-" + backlogSequence);

			projectTask.setProjectIdentifier(projectIdentifier);
			backlog.setPTSequence(backlogSequence);

			// setting default priority and status

			if (projectTask.getPriority() == null) {
				projectTask.setPriority(3);

			}
			if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TODO");
			}

			return projectTaskRepository.save(projectTask);

		}
		catch (Exception e) {
			throw new ProjectNotFoundException("Project Not Found");
		}

	}
	public Iterable<ProjectTask>findBacklogById(String id){
		Project project=projectRepository.findByProjectIdentifier(id);
		if(project==null) {
			throw new ProjectNotFoundException("Project with id: "+id+" not found");
		}
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}
	
	public ProjectTask findPTByProjectSequence(String backlog_id,String pt_id) {
		return projectTaskRepository.findByProjectSequence(pt_id);
	}

}
