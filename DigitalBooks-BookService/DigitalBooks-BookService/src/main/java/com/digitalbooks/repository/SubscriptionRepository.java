package com.digitalbooks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.digitalbooks.model.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

	public Subscription findBySubscriptionId(Long subscriptionId);

	@Query("SELECT s from Subscription s where s.userId=:userId")
	public List<Subscription> getUserSubscriptions(Long userId);
	
	@Query("SELECT s from Subscription s where s.bookId=:bookId")
	public List<Subscription> getUserSubscriptionsByBook(Long bookId);
	
}
