package com.digitalbooks.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digitalbooks.model.Book;
import com.digitalbooks.model.Subscription;
import com.digitalbooks.repository.BookRepository;
import com.digitalbooks.repository.SubscriptionRepository;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	
	@Autowired
	private BookRepository bookRepository;

	@Override
	public List<Subscription> getAllSubscriptions() {
		return subscriptionRepository.findAll();
	}

	@Override
	public List<Subscription> getAllUserSubscriptions(Long userId) {
		return subscriptionRepository.getUserSubscriptions(userId);
	}
	
	@Override
	public Subscription addSubscription(Long userId, Long bookId) {
		Subscription newSubscription = new Subscription();
		Book subscribedBook = bookRepository.findByBookId(bookId);
		if(subscribedBook == null) {
			//throw exception, also check if book is already subscribed
		}
		newSubscription.setUserId(userId);
		newSubscription.setBookId(bookId);
		newSubscription.setBookTitle(subscribedBook.getBookTitle());
		newSubscription.setSubscriptionDate(LocalDate.now());
		newSubscription.setSubscriptionPrice(subscribedBook.getBookPrice());
		return subscriptionRepository.save(newSubscription);
	}

	@Override
	public void deleteBySubscriptionId(Long subscriptionId) {
		subscriptionRepository.deleteBySubscriptionId(subscriptionId);
		
	}

}
