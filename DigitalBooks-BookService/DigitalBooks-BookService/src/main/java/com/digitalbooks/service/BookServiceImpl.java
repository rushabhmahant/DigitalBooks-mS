package com.digitalbooks.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.digitalbooks.model.Book;
import com.digitalbooks.model.Logo;
import com.digitalbooks.model.Subscription;
import com.digitalbooks.repository.BookRepository;
import com.digitalbooks.repository.LogoRepository;
import com.digitalbooks.repository.SubscriptionRepository;
import com.digitalbooks.valueobject.User;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired private LogoRepository logoRepository;
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	public List<Book> getAllBooks(){
		return bookRepository.findAll();
	}
	
	public Book getBookById(Long bookId) {
		return bookRepository.findById(bookId).get();
	}
	
	public List<Book> searchBook(String category, String title, String author, Double price) {
		String priceAsString = String.valueOf(price);
		if(price != null && !price.toString().isBlank()) {
			BigDecimal priceAsBigDecimal = new BigDecimal(priceAsString);
			int intValue = priceAsBigDecimal.intValue();
			String decimalValue = priceAsBigDecimal.subtract(
					  new BigDecimal(intValue)).toPlainString();
			if(Double.parseDouble(decimalValue) == 0) {
				priceAsString = String.valueOf(intValue);
			}
		}else {priceAsString="";}
		List<Book> booksFound = bookRepository.searchBook(category, title, author, priceAsString);
		System.out.println("Searching... title: "+title+", category: "+category
				+", author: "+author+", price: "+price);
		for(Book book: booksFound) {
			System.out.println(book);
		}
		return booksFound;
	}

	//	Readers APIs below

	@Override
	public List<Book> getUserSubscribedBooks(Long userId) {
		List<Subscription> userSubscriptions = subscriptionRepository.getUserSubscriptions(userId);
		List<Book> userSubscribedBooks = new ArrayList<Book>();
		for(Subscription s: userSubscriptions) {
			// perform a check if findByBookId(s.bookId) != null
			if(s.getSubscriptionStatus().equals('A')) {
				userSubscribedBooks.add(bookRepository.findByBookId(s.getBookId()));
			}
		}
		return userSubscribedBooks;
	}
	
	public Book createBook(Long authorId, Book book, Long logoId) {
		book.setAuthorId(authorId);
		System.out.println("LogoId received for create: " + logoId);
		if(logoId != null && !logoId.toString().isBlank()) {
		Logo bookLogo = logoRepository.findByLogoId(logoId);
		System.out.println(bookLogo.getLogoId()+", " + bookLogo.getLogoName());
			if(bookLogo != null) {
				book.setLogo(bookLogo);
			}
		}
		book.setBookPublishedDate(book.getBookPublishedDate().plusHours(5).plusMinutes(30));
		return bookRepository.save(book);
	}
	
	public Book updateBook(Long bookId, Book book, Long logoId) {
		book.setBookId(bookId);
		System.out.println("LogoId received for update: " + logoId);
		if(logoId != null && logoId>0) {
			Logo bookLogo = logoRepository.findByLogoId(logoId);
			System.out.println(bookLogo.getLogoId()+", " + bookLogo.getLogoName());
				if(bookLogo != null) {
					book.setLogo(bookLogo);
				}
			}
		book.setBookPublishedDate(book.getBookPublishedDate().plusHours(5).plusMinutes(30));
		return bookRepository.save(book);
	}

	public void deleteBook(Long bookId) {
		Book bookToDelete = bookRepository.findByBookId(bookId);
		
		List<Subscription> subscriptionList = subscriptionRepository.getUserSubscriptionsByBook(bookId);
		for(Subscription s: subscriptionList) {
			s.setSubscriptionStatus('I');
			subscriptionRepository.save(s);
		}
		
		if(bookToDelete.getLogo() != null && bookToDelete.getLogo().getLogoId()>0) {
			Logo logoToDelete = bookToDelete.getLogo();
			logoRepository.delete(logoToDelete);
			System.out.println(logoToDelete.getLogoId()+", " + logoToDelete.getLogoName() + " deleted");
		}
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
