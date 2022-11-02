package com.digitalbooks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.model.Subscription;
import com.digitalbooks.service.SubscriptionService;

@RestController
@CrossOrigin
@RequestMapping("/subscriptionservice")
public class SubscriptionController {
	
	@Autowired
	private SubscriptionService subscriptionService;

	@GetMapping("/readers/{userId}")
	public List<Subscription> getAllUserSubscriptions(@PathVariable Long userId){
		return subscriptionService.getAllUserSubscriptions(userId);
	}
	
	@PostMapping("/{userId}/subscribe/{bookId}")
	public Subscription subscribe(@PathVariable Long userId, @PathVariable Long bookId, @RequestBody Subscription subscription) {
		return subscriptionService.addSubscription(userId, bookId);
	}
	
	@DeleteMapping("/{userId}/removesubscription")
	public ResponseEntity<?> removeSubscription(@PathVariable Long userId, @RequestParam Long subscriptionId) {
		subscriptionService.deleteBySubscriptionId(subscriptionId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
