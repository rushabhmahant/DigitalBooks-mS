package com.digitalbooks.valueobject;

import java.time.LocalDate;

public class Subscription {
	
	private Long subscriptionId;
	private Long userId;
	private Long bookId;
	private String bookTitle;
	private Character subscriptionStatus;
	private LocalDate subscriptionDate;
	private Double subscriptionPrice;
	
	public Subscription() {
		// TODO Auto-generated constructor stub
	}

	public Subscription(Long subscriptionId, Long userId, Long bookId, String bookTitle, Character subscriptionStatus,
			LocalDate subscriptionDate, Double subscriptionPrice) {
		super();
		this.subscriptionId = subscriptionId;
		this.userId = userId;
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.subscriptionStatus = subscriptionStatus;
		this.subscriptionDate = subscriptionDate;
		this.subscriptionPrice = subscriptionPrice;
	}



	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Character getSubscriptionStatus() {
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(Character subscriptionStatus) {
		this.subscriptionStatus = subscriptionStatus;
	}

	public LocalDate getSubscriptionDate() {
		return subscriptionDate;
	}

	public void setSubscriptionDate(LocalDate subscriptionDate) {
		this.subscriptionDate = subscriptionDate;
	}

	public Double getSubscriptionPrice() {
		return subscriptionPrice;
	}

	public void setSubscriptionPrice(Double subscriptionPrice) {
		this.subscriptionPrice = subscriptionPrice;
	}
	
	

}
