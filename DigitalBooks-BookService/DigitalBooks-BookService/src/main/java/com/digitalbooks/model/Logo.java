package com.digitalbooks.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "logo")
@SequenceGenerator(name = "logoIdGenerator", sequenceName = "logoIdGenerator",  initialValue = 100)
public class Logo {
	
	//	For storing book logo
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "logoIdGenerator")
	private Long logoId;
	@Column
	private String logoName;
	@Column
	private String logoType;
	@Column(length = 1000)
	private byte[] logoBytes;
	
//	@OneToOne(mappedBy = "logo")
//	private Book book;
	
	public Logo() {
		// TODO Auto-generated constructor stub
	}

	public Logo(Long logoId, String logoName, String logoType, byte[] logoBytes, Book book) {
		super();
		this.logoId = logoId;
		this.logoName = logoName;
		this.logoType = logoType;
		this.logoBytes = logoBytes;
	}

	public Long getLogoId() {
		return logoId;
	}

	public void setLogoId(Long logoId) {
		this.logoId = logoId;
	}

	public String getLogoName() {
		return logoName;
	}

	public void setLogoName(String logoName) {
		this.logoName = logoName;
	}

	public String getLogoType() {
		return logoType;
	}

	public void setLogoType(String logoType) {
		this.logoType = logoType;
	}

	public byte[] getLogoBytes() {
		return logoBytes;
	}

	public void setLogoBytes(byte[] logoBytes) {
		this.logoBytes = logoBytes;
	}
	
	

}
