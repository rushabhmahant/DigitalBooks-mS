package com.digitalbooks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.exceptionhandling.BusinessException;
import com.digitalbooks.exceptionhandling.ControllerException;
import com.digitalbooks.model.User;
import com.digitalbooks.service.UserService;
import com.digitalbooks.valueobject.Book;
import com.digitalbooks.valueobject.ResponseTemplateUserSubscribedBooks;
import com.digitalbooks.valueobject.ResponseTemplateUserSubscriptions;
import com.digitalbooks.valueobject.Subscription;

@RestController
@RequestMapping("/userservice")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable Long userId){
		System.out.println("Userid in controller: " + userId);
		try {
			User user = userService.getUserById(userId);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		catch(BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
		catch(Exception e) {
			ControllerException ce = new ControllerException("701", "Exception occurred "+e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> userSignup(@RequestBody User user) {
		try {
			User newUser = userService.signUp(user);
			return new ResponseEntity<User>(newUser, HttpStatus.OK);
		}
		catch(BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} 
		catch(Exception e) {
			ControllerException ce = new ControllerException("701", "Exception occurred "+e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/dummysignup")
	public User dummySignUp(){
		return userService.dummySignUp();
	}
	
	//	APIs below are for Readers
	
	@GetMapping("/readers/{userId}/books")
	public ResponseTemplateUserSubscribedBooks getUserSubscribedBooks(@PathVariable Long userId){
		return userService.getUserSubscribedBooks(userId);
	}
	
	@GetMapping("/readers/{userId}/subscriptions")
	public ResponseTemplateUserSubscriptions getAllUserSubscriptions(@PathVariable Long userId){
		return userService.getAllUserSubscriptions(userId);
	}
	
	@PostMapping("/readers/{userId}/subscribe/{bookId}")
	public Subscription subscribe(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody Subscription subscription) {
		return userService.addSubscription(userId, bookId, subscription);
	}
	
	//	APIs below are accessible only to authors
	//	userId=authorId
	
	@PostMapping("/author/{userId}/books")		
	public ResponseEntity<?> createBook(@PathVariable Long userId, @RequestBody Book book) {
		try {
			Book newBook = userService.createBook(userId, book);
			return new ResponseEntity<Book>(newBook, HttpStatus.OK);
		}
		catch(BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} 
		catch(Exception e) {
			ControllerException ce = new ControllerException("701", "Exception occurred "+e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	@PutMapping("/author/{userId}/books/{bookId}")
	public ResponseEntity<?> updateBook(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody Book book) {
		// Make sure to include bookId in request to perform update
		try {
			Book updatedBook = userService.updateBook(userId, bookId, book);
			return new ResponseEntity<Book>(updatedBook, HttpStatus.OK);
		}
		catch(BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} 
		catch(Exception e) {
			ControllerException ce = new ControllerException("701", "Exception occurred "+e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/author/delete/{userId}/{bookId}")
	public void deleteBook(@PathVariable Long userId, @PathVariable Long bookId) {
		userService.deleteBook(userId, bookId);
	}
	
	@PostMapping("/author/{userId}/books/{bookId}")
	public ResponseEntity<?> setBookBlockedStatus(@PathVariable Long userId, @PathVariable Long bookId, @RequestParam String block,
			@RequestBody Book book) {
		try {
			Book updatedBook = userService.setBookBlockedStatus(userId, bookId, block, book);
			return new ResponseEntity<Book>(updatedBook, HttpStatus.OK);
		}
		catch(BusinessException be) {
			ControllerException ce = new ControllerException(be.getErrorCode(), be.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} 
		catch(Exception e) {
			ControllerException ce = new ControllerException("701", "Exception occurred "+e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/author/getAllBooks")
	public List<Book> getAllBooks(){
		return userService.getAllBooks();
	}

}
