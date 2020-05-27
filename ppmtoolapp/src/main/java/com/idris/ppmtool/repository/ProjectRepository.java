package com.idris.ppmtool.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.idris.ppmtool.domain.Project;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {

	Iterable<Project> findAllById(Iterable<Long> ids);

	Project findByProjectIdentifier(String projectIdentifier);

	@Override
	Iterable<Project> findAll();

}
