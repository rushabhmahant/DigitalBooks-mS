package com.digitalbooks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.digitalbooks.exceptionhandling.BusinessException;
import com.digitalbooks.model.Role;
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
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long userId) throws BusinessException {
		if(userId == null || userId.toString().length()==0) {
			throw new BusinessException("601", "Please provide a valid user id");
		}
		return userRepository.findByUserId(userId);
	}
	
	@Override
	public User signUp(User user, Long roleId) {
		if(user == null || user.getUsername().isBlank() || user.getUserPassword().isEmpty()) {
			throw new BusinessException("602", "Provide valid inputs for user");
		}
		if(roleId != 101 && roleId != 102) {
			throw new BusinessException("606", "Provide valid valid role id for user");
		}
		String password = user.getUserPassword();
		String encodedPassword = passwordEncoder.encode(password);
		user.setUserPassword(encodedPassword);
		user.addUserRole(new Role(roleId));
		return userRepository.save(user);
	}

	@Override
	public ResponseTemplateUserSubscribedBooks getUserSubscribedBooks(Long userId) {
		
		ResponseTemplateUserSubscribedBooks rt = new ResponseTemplateUserSubscribedBooks();
		User user = userRepository.findByUserId(userId);
		List<Book> userSubscribedBooks = restTemplate.getForObject("http://localhost:9191/bookservice/readers/"+userId, 
				List.class);
		rt.setUser(user);
		rt.setUserSubscribedBooks(userSubscribedBooks);
		return rt;
	}

	@Override
	public ResponseTemplateUserSubscriptions getAllUserSubscriptions(Long userId) {
		ResponseTemplateUserSubscriptions rt = new ResponseTemplateUserSubscriptions();
		User user = userRepository.findByUserId(userId);
		List<Subscription> userSubscriptions = restTemplate.getForObject("http://localhost:9191/subscriptionservice/readers/"+userId, 
				List.class);
		rt.setUser(user);
		rt.setUserSubscriptions(userSubscriptions);
		
		return rt;
	}

	@Override
	public Subscription addSubscription(Long userId, Long bookId, Subscription subscription) {
		Subscription newSubscription = restTemplate.postForObject("http://localhost:9191/subscriptionservice/"+userId+"/subscribe/"+bookId, 
				subscription, Subscription.class);
		return newSubscription;
	}
	
	//	APIs accessible to Authors

	@Override
	public Book createBook(Long userId, Book book) throws BusinessException {
		if(userId == null || userId.toString().length()==0) {
			throw new BusinessException("603", "Please provide a valid user id in the url");
		}
		return restTemplate.postForObject("http://localhost:9191/bookservice/author/"+userId+"/books", 
				book, Book.class);
	}

	@Override
	public Book updateBook(Long userId, Long bookId, Book book) throws BusinessException {
		if(userId == null || userId.toString().length()==0) {
			throw new BusinessException("603", "Please provide a valid user id in the url");
		}
		if(bookId == null || bookId.toString().length()==0) {
			throw new BusinessException("604", "Please provide a valid book id to be updated in the url");
		}
		return restTemplate.postForObject("http://localhost:9191/bookservice/author/"+userId+"/book/"+bookId, 
				book, Book.class);
		
	}

	@Override
	public void deleteBook(Long userId, Long bookId) {
		restTemplate.delete("http://localhost:9191/bookservice/author/delete/"+userId+"/"+bookId);
		
	}

	@Override
	public Book setBookBlockedStatus(Long userId, Long bookId, String block, Book book) throws BusinessException {
		if(userId == null || userId.toString().length()==0) {
			throw new BusinessException("603", "Please provide a valid user id in the url");
		}
		if(bookId == null || bookId.toString().length()==0) {
			throw new BusinessException("604", "Please provide a valid book id in the url for book status to be updated");
		}
		if(block.equalsIgnoreCase("yes") && book.getBookBlockedStatus().equals('B')) {
			throw new BusinessException("605", "Book is already blocked!");
		}
		if(!block.equalsIgnoreCase("yes") && book.getBookBlockedStatus().equals('U')) {
			throw new BusinessException("605", "Book is already unblocked!");
		}
		return restTemplate.postForObject("http://localhost:9191/bookservice/author/"+userId+"/books/"+bookId+"?block="+block,
				book, Book.class);
	}

	@Override
	public List<Book> getAllBooks() {
		return restTemplate.getForObject("http://localhost:9191/bookservice/books", List.class);
	}

	@Override
	public User assignRoleToUser(Long userId, Long roleId) {
		User user = userRepository.findByUserId(userId);
		user.addUserRole(new Role(roleId));
		return userRepository.save(user);
	}
	
	
	
	
	
	

}
