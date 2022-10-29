package com.digitalbooks.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbooks.model.Role;


public interface RoleService {
	
	public List<Role> addRolesInDb();

}
