package com.digitalbooks.service;

import org.springframework.web.multipart.MultipartFile;

import com.digitalbooks.model.Logo;

public interface LogoService {

	Logo getLogoById(Long logoId);

	Logo addLogo(MultipartFile logoFile);
	
	public byte[] compressBytes(byte[] data);
	
	public byte[] decompressBytes(byte[] data);

}
