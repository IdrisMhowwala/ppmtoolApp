package com.idris.ppmtool.repository;

import org.springframework.data.repository.CrudRepository;

import com.idris.ppmtool.domain.Backlog;

public interface BacklogRepository extends CrudRepository<Backlog, Long> {

	Backlog findByProjectIdentifier(String projecIdentifier);

}
