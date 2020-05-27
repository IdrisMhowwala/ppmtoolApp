package com.idris.ppmtool.repository;

import org.springframework.data.repository.CrudRepository;

import com.idris.ppmtool.domain.ProjectTask;

public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {

}
