package com.akin.junit.spring.bo.exception;

public class TechnicalFailureException extends Exception {

	private static final long serialVersionUID = 1L;

	public TechnicalFailureException(String errorMessage) {

		super(errorMessage);
	}
	
}
