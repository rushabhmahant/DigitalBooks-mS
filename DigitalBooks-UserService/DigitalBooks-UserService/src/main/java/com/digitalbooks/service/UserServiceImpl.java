package com.digitalbooks.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;

@Service
public class UserServiceImpl implements UserService {
	
	private String baseUrlLocal = "http://localhost:7002";
	private String baseUrlAws = "http://Digitalbooksbookservice-env.eba-w4wsiyef.ap-northeast-1.elasticbeanstalk.com";
	
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
		Optional<User> checkUser = userRepository.findByUsername(user.getUsername());
		if(checkUser.isPresent() && checkUser.get().getUsername().equals(checkUser.get().getUsername())){
			throw new BusinessException("609", "User is already registered, please chose another username");
		}
		String password = user.getUserPassword();
		String encodedPassword = passwordEncoder.encode(password);
		user.setUserPassword(encodedPassword);
		// user.addUserRole(new Role(roleId));	--> This was a hell problematic line
		user = userRepository.save(user);
		return assignRoleToUser(user.getUserId(), roleId);
	}

	@Override
	public ResponseTemplateUserSubscribedBooks getUserSubscribedBooks(Long userId) {
		
		ResponseTemplateUserSubscribedBooks rt = new ResponseTemplateUserSubscribedBooks();
		User user = userRepository.findByUserId(userId);
		List<Book> userSubscribedBooks = restTemplate.getForObject(baseUrlLocal + "/bookservice/readers/"+userId, 
				List.class);
		rt.setUser(user);
		rt.setUserSubscribedBooks(userSubscribedBooks);
		return rt;
	}

	@Override
	public ResponseTemplateUserSubscriptions getAllUserSubscriptions(Long userId) {
		ResponseTemplateUserSubscriptions rt = new ResponseTemplateUserSubscriptions();
		User user = userRepository.findByUserId(userId);
		List<Subscription> userSubscriptions = restTemplate.getForObject(baseUrlLocal + "/subscriptionservice/readers/"+userId, 
				List.class);
		rt.setUser(user);
		rt.setUserSubscriptions(userSubscriptions);
		
		return rt;
	}

	@Override
	public Subscription addSubscription(Long userId, Long bookId, Subscription subscription) {
		Subscription newSubscription = restTemplate.postForObject(baseUrlLocal+"/subscriptionservice/"+userId+"/subscribe/"+bookId, 
				subscription, Subscription.class);
		return newSubscription;
	}
	
	//	APIs accessible to Authors

	@Override
	public Book createBook(Long userId, Book book) throws BusinessException {
		if(userId == null || userId.toString().length()==0) {
			throw new BusinessException("603", "Please provide a valid user id in the url");
		}
		return restTemplate.postForObject(baseUrlLocal+"/bookservice/author/"+userId+"/books", 
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
		return restTemplate.postForObject(baseUrlLocal+"/bookservice/author/"+userId+"/book/"+bookId, 
				book, Book.class);
		
	}

	@Override
	public void deleteBook(Long userId, Long bookId) {
		restTemplate.delete(baseUrlLocal+"/bookservice/author/delete/"+userId+"/"+bookId);
		
	}

	@Override
	public Book setBookBlockedStatus(Long userId, Long bookId, String block, Book book) throws BusinessException, JsonMappingException, JsonProcessingException {
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
		
		Character bookStatus = (block.equals("yes")) ? 'B' : 'U';
		String subscriptionStatus = (block.equals("yes")) ? "inactive" : "active";
		if(!bookStatus.equals(book.getBookBlockedStatus())) {
			book.setBookId(bookId);
			book.setAuthorId(userId);
			book.setBookBlockedStatus(bookStatus);
			updateBook(userId, bookId, book);
			
			String jsonString = 
					restTemplate.getForObject(baseUrlLocal+"/subscriptionservice/author/"+bookId, String.class);
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JSR310Module());
				List<Subscription> userSubscriptions = objectMapper.readValue(jsonString, new TypeReference<List<Subscription>>() {});
				List<Subscription> subscriptionList = new ArrayList<Subscription>(userSubscriptions);
				for(Subscription s: subscriptionList) {
					User user = userRepository.findByUserId(s.getUserId());
					restTemplate.put(baseUrlLocal+"/subscriptionservice/"+user.getUserId()+"/updatesubscriptionstatus/"+s.getSubscriptionId()+"/"+subscriptionStatus, "");
					if(bookStatus.equals('B')) {
					System.out.println("Notification for user " + user.getUsername() + 
							": The book you subscribed " + book.getBookTitle() + 
							" is blocked and no longer available.");
				}
			}
		}
		return book;
		 
//		return restTemplate.postForObject("http://localhost:9191/bookservice/author/"+userId+"/books/"+bookId+"?block="+block,
//				book, Book.class);
	}

	@Override
	public List<Book> getAllBooks() {
		return restTemplate.getForObject(baseUrlLocal+"/bookservice/books", List.class);
	}

	@Override
	public User assignRoleToUser(Long userId, Long roleId) {
		User user = userRepository.findByUserId(userId);
		if(userId == null || userId.toString().length()==0) {
			throw new BusinessException("603", "Please provide a valid user id in the url");
		}
		user.addUserRole(new Role(roleId));
		return userRepository.save(user);
	}

	@Override
	public Book getBookById(Long userId, Long bookId) {
		System.out.println("User " + userId + " is reading book " + bookId);
		return restTemplate.getForObject(baseUrlLocal+"/bookservice/book/bookId", Book.class);
	}

	@Override
	public ResponseEntity<?> removeSubscription(Long userId, Long subscriptionId) {
		restTemplate.delete(baseUrlLocal+"/subscriptionservice/"+userId + "/removesubscription/" + subscriptionId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
	
	
	

}
