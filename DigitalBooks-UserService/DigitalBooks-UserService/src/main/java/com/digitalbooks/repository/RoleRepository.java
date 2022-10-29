package com.digitalbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.digitalbooks.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	public Role findByRoleId(Long roleId);

}
