package com.idris.ppmtool.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.idris.ppmtool.domain.ProjectTask;

public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long>{

	List<ProjectTask> findByProjectIdentifierOrderByPriority(String id);
	
	ProjectTask findByProjectSequence(String sequence);
	
}
