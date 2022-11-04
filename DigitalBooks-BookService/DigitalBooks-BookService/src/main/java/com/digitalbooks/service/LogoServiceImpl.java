package com.digitalbooks.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.digitalbooks.model.Logo;
import com.digitalbooks.repository.LogoRepository;

@Service
public class LogoServiceImpl implements LogoService{
	
	@Autowired
	private LogoRepository logoRepository;

	@Override
	public Logo getLogoById(Long logoId) {
		Logo logo = logoRepository.findByLogoId(logoId);
		if(logo != null && logo.getLogoBytes().length>0) {
			logo.setLogoBytes(decompressBytes(logo.getLogoBytes()));
		}
		return logo;
	}

	@Override
	public Logo addLogo(MultipartFile logoFile) {
		Logo imageLogo = new Logo();
		imageLogo.setLogoName(logoFile.getOriginalFilename());
		imageLogo.setLogoType(logoFile.getContentType());
		try {
			imageLogo.setLogoBytes(compressBytes(logoFile.getBytes()));
		} catch (IOException e) {
			System.out.println("IOException: " + e.toString());
		}
		
		return logoRepository.save(imageLogo);
	}
	
	@Override
	public byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

		return outputStream.toByteArray();
	}
	
	@Override
	public byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}

}
