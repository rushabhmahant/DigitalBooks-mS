package com.digitalbooks.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbooks.model.Role;
import com.digitalbooks.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	
	public List<Role> addRolesInDb() {
		Role r1 = new Role();
		r1.setRoleName("ROLE_AUTHOR");
		Role r2 = new Role();
		r2.setRoleName("ROLE_READER");
		List<Role> roles = new ArrayList<Role>();
		roles.add(r1);
		roles.add(r2);
		return roleRepository.saveAll(roles);
		
	}

}
