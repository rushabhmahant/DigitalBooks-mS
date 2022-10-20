package com.digitalbooks.valueobject;

import java.time.LocalDate;

public class Book {
	
	private Long bookId;
	private String bookTitle;
	private String bookCategory;
	private String bookAuthor;
	private Double bookPrice;
	private String bookLogo;
	private String bookContent;
	private String bookPublisher;
	private LocalDate bookPublishedDate;
	private Character bookBlockedStatus;
	
	
	public Book() {
	}


	public Book(Long bookId, String bookTitle, String bookCategory, String bookAuthor, Double bookPrice,
			String bookLogo, String bookContent, String bookPublisher, LocalDate bookPublishedDate,
			Character bookBlockedStatus) {
		super();
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.bookCategory = bookCategory;
		this.bookAuthor = bookAuthor;
		this.bookPrice = bookPrice;
		this.bookLogo = bookLogo;
		this.bookContent = bookContent;
		this.bookPublisher = bookPublisher;
		this.bookPublishedDate = bookPublishedDate;
		this.bookBlockedStatus = bookBlockedStatus;
	}


	public Long getBookId() {
		return bookId;
	}


	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}


	public String getBookTitle() {
		return bookTitle;
	}


	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}


	public String getBookCategory() {
		return bookCategory;
	}


	public void setBookCategory(String bookCategory) {
		this.bookCategory = bookCategory;
	}


	public String getBookAuthor() {
		return bookAuthor;
	}


	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}


	public Double getBookPrice() {
		return bookPrice;
	}


	public void setBookPrice(Double bookPrice) {
		this.bookPrice = bookPrice;
	}


	public String getBookLogo() {
		return bookLogo;
	}


	public void setBookLogo(String bookLogo) {
		this.bookLogo = bookLogo;
	}


	public String getBookContent() {
		return bookContent;
	}


	public void setBookContent(String bookContent) {
		this.bookContent = bookContent;
	}


	public String getBookPublisher() {
		return bookPublisher;
	}


	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}


	public LocalDate getBookPublishedDate() {
		return bookPublishedDate;
	}


	public void setBookPublishedDate(LocalDate bookPublishedDate) {
		this.bookPublishedDate = bookPublishedDate;
	}


	public Character getBookBlockedStatus() {
		return bookBlockedStatus;
	}


	public void setBookBlockedStatus(Character bookBlockedStatus) {
		this.bookBlockedStatus = bookBlockedStatus;
	}

}
