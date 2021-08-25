package com.example.spring_luxoft_boot.repository;

public class CountryNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public CountryNotFoundException(Throwable cause) {
		super(cause);
	}
}
