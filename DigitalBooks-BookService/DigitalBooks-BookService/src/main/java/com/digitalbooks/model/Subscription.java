package com.digitalbooks.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "subscription")
@SequenceGenerator(name = "subscriptionIdGenerator", sequenceName = "subscriptionIdGenerator",  initialValue = 100000)
public class Subscription {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subscriptionIdGenerator")
	private Long subscriptionId;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = false)
	private Long bookId;
	@Column(nullable = false)
	private Character subscriptionStatus = 'A';	// Subscription status set to Active by default
	@Column(nullable = false)
	private LocalDate subscriptionDate;
	@Column(nullable = false)
	private Double subscriptionPrice;
	
	public Subscription() {
		// Default constructor
	}

	public Subscription(Long userId, Long bookId, Character subscriptionStatus,
			LocalDate subscriptionDate, Double subscriptionPrice) {
		super();
		//this.userId = userId;
		this.bookId = bookId;
		this.subscriptionStatus = subscriptionStatus;
		this.subscriptionDate = subscriptionDate;
		this.subscriptionPrice = subscriptionPrice;
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

	public Long getSubscriptionId() {
		return subscriptionId;
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
	

}
