package com.digitalbooks.service;

import java.util.List;

import com.digitalbooks.model.Subscription;

public interface SubscriptionService {
	
	public List<Subscription> getAllSubscriptions();

	public List<Subscription> getAllUserSubscriptions(Long userId);
	
	public Subscription addSubscription(Long userId, Long bookId);

	public void deleteBySubscriptionId(Long subscriptionId);

	public List<Subscription> getUserSubscriptionsByBook(Long bookId);

	public Subscription updateSubscriptionStatus(Long subscriptionId, String status);

}
