package com.trier.gerenciamentoestoque.services.exceptions;

public class IntegrityViolation extends RuntimeException {
	
	public IntegrityViolation(String message) {
		super(message);
	}
	
}
