package com.digitalbooks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.digitalbooks.model.Logo;
import com.digitalbooks.model.Subscription;
import com.digitalbooks.service.LogoService;

@RestController
@CrossOrigin
@RequestMapping("/logoservice")
public class LogoController {
	
	@Autowired
	private LogoService logoService;
	
	@GetMapping("/logo/{logoId}")
	public Logo getLogoById(@PathVariable Long logoId){
		return logoService.getLogoById(logoId);
	}
	
	@PostMapping("/logo")
	public Logo addLogo(@RequestParam(required = false) MultipartFile logoFile) {
		
		return logoService.addLogo(logoFile);
	}

}
