package com.digitalbooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.digitalbooks.model.User;
import com.digitalbooks.repository.UserRepository;
import com.digitalbooks.valueobject.Book;
import com.digitalbooks.valueobject.ResponseTemplateUserSubscribedBooks;
import com.digitalbooks.valueobject.ResponseTemplateUserSubscriptions;
import com.digitalbooks.valueobject.Subscription;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long userId) {
		return userRepository.findByUserId(userId);
	}
	
	@Override
	public User signUp(User user) {
		return userRepository.save(user);
	}

	@Override
	public User dummySignUp() {
		User user = new User();
		user.setUserFirstName("raj");
		user.setUserLastName("rai");
		user.setUsername("raj.rai@abc.com");
		user.setUserPassword("raj123");
		user.setUserAccountType('R');
		return userRepository.save(user);
	}

	@Override
	public ResponseTemplateUserSubscribedBooks getUserSubscribedBooks(Long userId) {
		
		ResponseTemplateUserSubscribedBooks rt = new ResponseTemplateUserSubscribedBooks();
		User user = userRepository.findByUserId(userId);
		List<Book> userSubscribedBooks = restTemplate.getForObject("http://localhost:7002/bookservice/readers/"+userId, 
				List.class);
		return rt;
	}

	@Override
	public ResponseTemplateUserSubscriptions getAllUserSubscriptions(Long userId) {
		ResponseTemplateUserSubscriptions rt = new ResponseTemplateUserSubscriptions();
		User user = userRepository.findByUserId(userId);
		List<Subscription> userSubscriptions = restTemplate.getForObject("http://localhost:7002/subscriptionservice/readers/"+userId, 
				List.class);
		rt.setUser(user);
		rt.setUserSubscriptions(userSubscriptions);
		
		return rt;
	}

	@Override
	public Subscription addSubscription(Long userId, Long bookId, Subscription subscription) {
		Subscription newSubscription = restTemplate.postForObject("http://localhost:7002/subscriptionservice/"+userId+"/subscribe"+bookId, 
				subscription, Subscription.class);
		return newSubscription;
	}
	
	//	APIs accessible to Authors

	@Override
	public Book createBook(Long userId, Book book) {
		return restTemplate.postForObject("http://localhost:7002/bookservice/author/"+userId+"/books", 
				book, Book.class);
	}

	@Override
	public Book updateBook(Long userId, Long bookId, Book book) {
		return restTemplate.postForObject("http://localhost:7002/bookservice/author/"+userId+"/book/"+bookId, 
				book, Book.class);
		
	}

	@Override
	public void deleteBook(Long userId, Long bookId) {
		restTemplate.delete("http://localhost:7002/bookservice/author/delete/"+userId+"/"+bookId);
		
	}

	@Override
	public Book setBookBlockedStatus(Long userId, Long bookId, String block, Book book) {
		return restTemplate.postForObject("http://localhost:7002/bookservice/author/"+userId+"/books/"+bookId,
				book, Book.class);
	}
	
	
	
	
	
	

}
