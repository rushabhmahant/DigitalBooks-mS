package com.digitalbooks.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.digitalbooks.model.Book;
import com.digitalbooks.model.Subscription;
import com.digitalbooks.repository.BookRepository;
import com.digitalbooks.repository.SubscriptionRepository;
import com.digitalbooks.valueobject.User;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	public List<Book> getAllBooks(){
		return bookRepository.findAll();
	}
	
	public Book getBookById(Long bookId) {
		return bookRepository.findById(bookId).get();
	}
	
	public List<Book> searchBook(String category, String title, String author, Double price) {		
		return bookRepository.searchBook(category, title, author, price);
	}

	//	Readers APIs below

	@Override
	public List<Book> getUserSubscribedBooks(Long userId) {
		List<Subscription> userSubscriptions = subscriptionRepository.getUserSubscriptions(userId);
		List<Book> userSubscribedBooks = new ArrayList<Book>();
		for(Subscription s: userSubscriptions) {
			// perform a check if findByBookId(s.bookId) != null
			userSubscribedBooks.add(bookRepository.findByBookId(s.getBookId()));
		}
		return userSubscribedBooks;
	}
	
	public Book createBook(Long authorId, Book book) {
		book.setAuthorId(authorId);
		return bookRepository.save(book);
	}
	
	public Book updateBook(Long bookId, Book book) {
		book.setBookId(bookId);
		return bookRepository.save(book);
	}

	public void deleteBook(Long bookId) {
		bookRepository.deleteById(bookId);
	}

	public Book setBookBlockedStatus(Long authorId, Long bookId, String block, Book book) {
		Character bookStatus = (block.equals("yes")) ? 'B' : 'U';
		if(!bookStatus.equals(book.getBookBlockedStatus())) {
			book.setBookId(bookId);
			book.setAuthorId(authorId);
			book.setBookBlockedStatus(bookStatus);
			bookRepository.save(book);
			if(bookStatus.equals('B')) {
				List<Subscription> userSubscriptions = 
						subscriptionRepository.getUserSubscriptionsByBook(bookId);
				User user = null;
				for(Subscription s: userSubscriptions) {
					user = restTemplate.getForObject("http://localhost:7001/api/v1/digitalbooks/userservice/user/"+s.getUserId(), User.class);
					System.out.println("Notification for user " + user.getUsername() + 
							": The book you subscribed " + book.getBookTitle() + 
							" is blocked and no longer available.");
				}
			}
		}
		return book;
	}
	
}
