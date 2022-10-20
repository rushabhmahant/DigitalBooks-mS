package com.digitalbooks.valueobject;

import java.util.List;

import com.digitalbooks.model.User;

public class ResponseTemplateUserSubscriptions {
	
	private User user;
	private List<Subscription> userSubscriptions;
	
	public ResponseTemplateUserSubscriptions() {
		// TODO Auto-generated constructor stub
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Subscription> getUserSubscriptions() {
		return userSubscriptions;
	}

	public void setUserSubscriptions(List<Subscription> userSubscriptions) {
		this.userSubscriptions = userSubscriptions;
	}

	

}
