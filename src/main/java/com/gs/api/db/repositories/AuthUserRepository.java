
/**
 * 
 * @author norirman
 * @desc -
 * © 2024 snoriman@gmail.com
 * Zurich POC for insurance open api
 */
package com.gs.api.db.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gs.api.db.entity.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
	AuthUser findByUsername(String username);
	AuthUser findByToken(String token);
	

}
