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

import com.digitalbooks.model.Book;
import com.digitalbooks.model.Subscription;
import com.digitalbooks.service.BookService;

@RestController
@RequestMapping("/bookservice")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	// Generalised APIs -> Can be accessed by everyone (Guests, readers and authors)
	
	@GetMapping("/books")
	public List<Book> getAllBooks(){
		return bookService.getAllBooks();
	}
	
	@GetMapping("/search")
	public List<Book> searchBook(@RequestParam(required = false) String category,
			@RequestParam(required = false) String title,
			@RequestParam(required = false) String author,
			@RequestParam(required = false) Double price,
			@RequestParam(required = false) String publisher) {
		return bookService.searchBook(category, title, author, price, publisher);
	}
	
	@GetMapping("/book/{bookId}")
	public Book getBookById(@PathVariable Long bookId) {
		return bookService.getBookById(bookId);
	}
	
	//	APIs below are for Readers
	
	@GetMapping("/readers/{userId}")
	public List<Book> getUserSubscribedBooks(@PathVariable Long userId){
		return bookService.getUserSubscribedBooks(userId);
	}
	
	//	APIs below are accessible only to authors
	
	@PostMapping("/author/{authorId}/books")
	public Book createBook(@PathVariable Long authorId, @RequestBody Book book) {
		
		return bookService.createBook(authorId, book);
	}
	
	@PutMapping("/author/{authorId}/books/{bookId}")
	public Book updateBook(@PathVariable Long bookId, @RequestBody Book book) {
		// Make sure to include bookId in request to perform update
		return bookService.updateBook(bookId, book);
	}
	
	@DeleteMapping("/author/delete/{authorId}/{bookId}")
	public void deleteBook(@PathVariable Long bookId) {
		
		bookService.deleteBook(bookId);
	}
	
	@PostMapping("/author/{authorId}/books/{bookId}")
	public Book setBookBlockedStatus(@PathVariable Long authorId, @PathVariable Long bookId, @RequestParam String block,
			@RequestBody Book book) {
		return bookService.setBookBlockedStatus(authorId, bookId, block, book);
	}

}
