package com.digitalbooks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
	public User getUserById(@PathVariable Long userId){
		return userService.getUserById(userId);
	}
	
	@PostMapping("/signup")
	public User userSignup(User user) {
		return userService.signUp(user);
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
	public Book createBook(@PathVariable Long userId, @RequestBody Book book) {
		return userService.createBook(userId, book);
	}
	
	@PutMapping("/author/{userId}/books/{bookId}")
	public Book updateBook(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody Book book) {
		// Make sure to include bookId in request to perform update
		return userService.updateBook(userId, bookId, book);
	}
	
	@DeleteMapping("/author/delete/{userId}/{bookId}")
	public void deleteBook(@PathVariable Long userId, @PathVariable Long bookId) {
		userService.deleteBook(userId, bookId);
	}
	
	@PostMapping("/author/{authorId}/books/{bookId}")
	public Book setBookBlockedStatus(@PathVariable Long userId, @PathVariable Long bookId, @RequestParam String block,
			@RequestBody Book book) {
		return userService.setBookBlockedStatus(userId, bookId, block, book);
	}

}
