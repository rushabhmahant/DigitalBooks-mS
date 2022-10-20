package com.digitalbooks.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@SequenceGenerator(name = "userIdGenerator", sequenceName = "userIdGenerator",  initialValue = 10000)
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "userIdGenerator")
	private Long userId;
	@Column(nullable = false)
	private String username;
	@Column(nullable = false)
	private String userPassword;
	@Column(nullable = false)
	private Character userAccountType;	// 'R' or 'A'
	@Column(nullable = false)
	private String userFirstName;
	@Column(nullable = false)
	private String userLastName;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(Long userId, String username, String userPassword, Character userAccountType, String userFirstName,
			String userLastName) {
		super();
		this.userId = userId;
		this.username = username;
		this.userPassword = userPassword;
		this.userAccountType = userAccountType;
		this.userFirstName = userFirstName;
		this.userLastName = userLastName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Character getUserAccountType() {
		return userAccountType;
	}

	public void setUserAccountType(Character userAccountType) {
		this.userAccountType = userAccountType;
	}

	public String getUserFirstName() {
		return userFirstName;
	}

	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}

	public String getUserLastName() {
		return userLastName;
	}

	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	
	

}
