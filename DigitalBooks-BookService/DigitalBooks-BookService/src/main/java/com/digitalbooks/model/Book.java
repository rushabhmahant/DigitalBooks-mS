package com.digitalbooks.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "book")
@SequenceGenerator(name = "bookIdGenerator", sequenceName = "bookIdGenerator",  initialValue = 1000)
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "bookIdGenerator")
	private Long bookId;
	@Column(nullable = false)
	private String bookTitle;
	@Column(nullable = false)
	private String bookCategory;
	@Column(nullable = false)
	private Long authorId;
	@Column(nullable = false)
	private String bookAuthor;
	@Column(nullable = false, precision=8, scale=2)
	private Double bookPrice;
	@Column(nullable = false)
	private String bookLogo;
	@Column(nullable = false)
	private String bookContent;
	@Column(nullable = false)
	private String bookPublisher;
	@Column(nullable = false)
	private LocalDateTime bookPublishedDate;
	@Column(nullable = false)
	private Character bookBlockedStatus = 'U';	// Book is unblocked by default
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_logo_id")
	private Logo logo;
	
	public Book() {
	}


	public Book(Long bookId, String bookTitle, String bookCategory, Long authorId, String bookAuthor, Double bookPrice,
			String bookLogo, String bookContent, String bookPublisher, LocalDateTime bookPublishedDate,
			Character bookBlockedStatus) {
		super();
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.bookCategory = bookCategory;
		this.authorId = authorId;
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


	public LocalDateTime getBookPublishedDate() {
		return bookPublishedDate;
	}


	public void setBookPublishedDate(LocalDateTime bookPublishedDate) {
		this.bookPublishedDate = bookPublishedDate;
	}


	public Character getBookBlockedStatus() {
		return bookBlockedStatus;
	}


	public void setBookBlockedStatus(Character bookBlockedStatus) {
		this.bookBlockedStatus = bookBlockedStatus;
	}


	public Long getAuthorId() {
		return authorId;
	}


	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}


	public Logo getLogo() {
		return logo;
	}


	public void setLogo(Logo logo) {
		this.logo = logo;
	}


	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookTitle=" + bookTitle + ", bookCategory=" + bookCategory + ", authorId="
				+ authorId + ", bookAuthor=" + bookAuthor + ", bookPrice=" + bookPrice + ", bookLogo=" + bookLogo
				+ ", bookContent=" + bookContent + ", bookPublisher=" + bookPublisher + ", bookPublishedDate="
				+ bookPublishedDate + ", bookBlockedStatus=" + bookBlockedStatus + ", logo=" + logo + "]";
	}
	
	
	

}
