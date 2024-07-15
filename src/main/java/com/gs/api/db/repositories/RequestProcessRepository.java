package com.gs.api.db.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gs.api.db.entity.RequestProcess;

public interface RequestProcessRepository extends JpaRepository<RequestProcess, Long> {

	 List<RequestProcess> findByStatus(String status);
}