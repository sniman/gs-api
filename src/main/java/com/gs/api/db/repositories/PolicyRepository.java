package com.gs.api.db.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gs.api.db.entity.Policy;


public interface PolicyRepository extends JpaRepository<Policy, Long> {
	Policy findByPolicyId(String id);
	List<Policy> findByStatus(String statys);
}
