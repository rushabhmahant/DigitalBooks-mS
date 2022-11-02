package com.digitalbooks.service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;

import com.digitalbooks.exceptionhandling.BusinessException;
import com.digitalbooks.model.User;
import com.digitalbooks.valueobject.Book;
import com.digitalbooks.valueobject.ResponseTemplateUserSubscribedBooks;
import com.digitalbooks.valueobject.ResponseTemplateUserSubscriptions;
import com.digitalbooks.valueobject.Subscription;

public interface UserService {
	
	public List<User> getAllUsers();
	
	public User getUserById(Long userId) throws BusinessException;
	
	public User signUp(User user, Long roleId) throws BusinessException;

	public ResponseTemplateUserSubscribedBooks getUserSubscribedBooks(Long userId);

	public ResponseTemplateUserSubscriptions getAllUserSubscriptions(Long userId);

	public Subscription addSubscription(Long userId, Long bookId, Subscription subscription);

	public Book createBook(Long userId, Book book) throws BusinessException;

	public Book updateBook(Long userId, Long bookId, Book book) throws BusinessException;

	public void deleteBook(Long userId, Long bookId);

	public Book setBookBlockedStatus(Long userId, Long bookId, String block, Book book)
		throws BusinessException;

	public List<Book> getAllBooks();

	public User assignRoleToUser(Long userId, Long roleId);
	
	

}
