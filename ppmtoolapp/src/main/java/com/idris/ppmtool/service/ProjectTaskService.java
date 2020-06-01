package com.idris.ppmtool.service;

import java.util.List;

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

			if (projectTask.getPriority() == null || projectTask.getPriority()==0) {
				projectTask.setPriority(3);   //low priority

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
		//make sure that backlog id exist
		Backlog backlog=backlogRepository.findByProjectIdentifier(backlog_id);
		if(backlog==null) {
			throw new ProjectNotFoundException("Project not Found for id: "+backlog_id);
		}
		//make sure that project task id exists
		ProjectTask projectTask=projectTaskRepository.findByProjectSequence(pt_id);
		if(projectTask==null) {
			throw new ProjectNotFoundException("Project task with id: "+pt_id+" does not exist");
		}
		//make sure backlog id and project identifier is same
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Backlog id: "+backlog_id+" does not match with project"
					+ " Identifier: "+projectTask.getProjectIdentifier());
		}
		return projectTask;
	}
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id) {
		//find the existing project task
		ProjectTask projectTask=findPTByProjectSequence(backlog_id, pt_id);
		//replace project task with updated task
		projectTask=updatedTask;
		
		return projectTaskRepository.save(projectTask);
	}
	
	public void deletePTByProjectSequence(String backlog_id,String pt_id) {
		ProjectTask projectTask=findPTByProjectSequence(backlog_id, pt_id);
		
		Backlog backlog=projectTask.getBacklog();
		List<ProjectTask> pts=backlog.getProjectTasks();
		pts.remove(projectTask);
		backlogRepository.save(backlog);
		projectTaskRepository.delete(projectTask);
		
	}

}
