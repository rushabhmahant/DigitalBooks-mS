package com.digitalbooks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "role")
@SequenceGenerator(name = "roleIdGenerator", sequenceName = "roleIdGenerator",  initialValue = 101)
public class Role {
	
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "roleIdGenerator")
    private Long roleId;
     
    @Column(nullable = false, length = 50, unique = true)
    private String roleName;
    
    public Role() {
	}
    
    public Role(Long roleId){
    	this.roleId = roleId;
    }
    
    public Role(String roleName){
    	this.roleName = roleName;
    }
    
 
    public Long getRoleId() {
		return roleId;
	}


	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}


	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	@Override
    public String toString() {
        return this.roleName;
    }
    
}
