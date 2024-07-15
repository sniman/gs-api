package com.gs.api.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gs.api.db.entity.Policy;


public interface PolicyRepository extends JpaRepository<Policy, Long> {
	Policy findByPolicyId(String id);
}
