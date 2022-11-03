package com.digitalbooks.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.exceptionhandling.BusinessException;
import com.digitalbooks.exceptionhandling.ControllerException;
import com.digitalbooks.jwtutil.JWTUtil;
import com.digitalbooks.model.Role;
import com.digitalbooks.model.User;
import com.digitalbooks.service.RoleService;
import com.digitalbooks.service.UserService;
import com.digitalbooks.valueobject.AuthRequest;
import com.digitalbooks.valueobject.AuthResponse;
import com.digitalbooks.valueobject.Book;
import com.digitalbooks.valueobject.ResponseTemplateUserSubscribedBooks;
import com.digitalbooks.valueobject.ResponseTemplateUserSubscriptions;
import com.digitalbooks.valueobject.Subscription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@CrossOrigin
@RequestMapping("/userservice")
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@RolesAllowed({"ROLE_AUTHOR","ROLE_READER"})
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@RolesAllowed({"ROLE_AUTHOR","ROLE_READER"})
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
	
	@PostMapping("/signup/{roleId}")
	public ResponseEntity<?> userSignup(@PathVariable Long roleId, @RequestBody User user) {
		try {
			User newUser = userService.signUp(user, roleId);
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
	
	//	APIs below are for Readers
	
	@RolesAllowed({"ROLE_READER"})
	@GetMapping("/readers/{userId}/book/{bookId}")
	public Book getBookById(@PathVariable Long userId, @PathVariable Long bookId){
		return userService.getBookById(userId, bookId);
	}
	
	@RolesAllowed({"ROLE_READER"})
	@GetMapping("/readers/{userId}/books")
	public ResponseTemplateUserSubscribedBooks getUserSubscribedBooks(@PathVariable Long userId){
		return userService.getUserSubscribedBooks(userId);
	}
	
	@RolesAllowed({"ROLE_READER"})
	@GetMapping("/readers/{userId}/subscriptions")
	public ResponseTemplateUserSubscriptions getAllUserSubscriptions(@PathVariable Long userId){
		return userService.getAllUserSubscriptions(userId);
	}
	
	@RolesAllowed({"ROLE_READER"})
	@PostMapping("/readers/{userId}/subscribe/{bookId}")
	public Subscription subscribe(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody Subscription subscription) {
		return userService.addSubscription(userId, bookId, subscription);
	}
	
	//	APIs below are accessible only to authors
	//	userId=authorId
	
	@RolesAllowed({"ROLE_AUTHOR"})
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
	
	@RolesAllowed({"ROLE_AUTHOR"})
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
	
	@RolesAllowed({"ROLE_AUTHOR"})
	@DeleteMapping("/author/delete/{userId}/{bookId}")
	public void deleteBook(@PathVariable Long userId, @PathVariable Long bookId) {
		userService.deleteBook(userId, bookId);
	}
	
	@RolesAllowed({"ROLE_AUTHOR"})
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
//		catch(Exception e) {
//			ControllerException ce = new ControllerException("701", "Exception occurred "+e.getMessage());
//			return new ResponseEntity<ControllerException>(ce, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
 catch (JsonMappingException e) {
			System.out.println("JSONMappingException");
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
		try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword())
            );
            System.out.println("Authentication object obtained..."); 
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(user);
            System.out.println("Access token for " + user.getUsername() +", "+ user.getPassword() + " obtained: " + accessToken);
            AuthResponse response = new AuthResponse(user.getUsername(), accessToken);
            System.out.println("Sending success response..."); 
            return ResponseEntity.ok().body(response);
             
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
	}
	/*****************Less Importatnt APIs below*******************/
	
	//@RolesAllowed({"ROLE_AUTHOR","ROLE_READER"})
	@GetMapping("/author/getAllBooks")
	public List<Book> getAllBooks(){
		return userService.getAllBooks();
	}
	
	@GetMapping("/role/addRoles")
	public List<Role> addRolesInDb() {
		return this.roleService.addRolesInDb();
	}
	
	@PostMapping("/role/{userId}")
	public User assignRoleToUser(@PathVariable Long userId,
			@RequestParam Long roleId) {
		return userService.assignRoleToUser(userId, roleId);
	}

}
