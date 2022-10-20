package com.digitalbooks.service;

import java.util.List;

import com.digitalbooks.model.Book;
import com.digitalbooks.model.Subscription;

public interface BookService {
	
	public List<Book> getAllBooks();
	
	public Book getBookById(Long bookId);

	public List<Book> getUserSubscribedBooks(Long userId);
	
	public Book createBook(Book book);
	
	public Book updateBook(Long bookId, Book book);
	
	public List<Book> searchBook(String category, String title, String author, Double price, String publisher);
	
	public void deleteBook(Long bookId);
	
	public Book setBookBlockedStatus(Long bookId, String block, Book book);
	
	
	
	

}
