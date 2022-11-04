package com.digitalbooks.valueobject;

public class Logo {
	
	private Long logoId;
	private String logoName;
	private String logoType;
	private byte[] logoBytes;
	
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
