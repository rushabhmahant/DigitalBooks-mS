package com.digitalbooks.valueobject;

public class User {
	
	private Long userId;
	private String username;
	private String userPassword;
	private String userFirstName;
	private String userLastName;
	
	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(Long userId, String username, String userPassword, String userFirstName,
			String userLastName) {
		super();
		this.userId = userId;
		this.username = username;
		this.userPassword = userPassword;
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
