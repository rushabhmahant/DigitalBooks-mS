package com.digitalbooks.valueobject;

import java.util.List;

import com.digitalbooks.model.User;

public class ResponseTemplateUserSubscribedBooks {
	
	private User user;
	private List<Book> userSubscribedBooks;
	
	public ResponseTemplateUserSubscribedBooks() {
		// TODO Auto-generated constructor stub
	}
	
	public ResponseTemplateUserSubscribedBooks(User user, List<Book> userSubscribedBooks) {
		super();
		this.user = user;
		this.userSubscribedBooks = userSubscribedBooks;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Book> getUserSubscribedBooks() {
		return userSubscribedBooks;
	}

	public void setUserSubscribedBooks(List<Book> userSubscribedBooks) {
		this.userSubscribedBooks = userSubscribedBooks;
	}
	
	

}
