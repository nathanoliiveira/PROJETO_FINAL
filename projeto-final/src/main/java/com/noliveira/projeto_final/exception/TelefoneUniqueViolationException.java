package com.noliveira.projeto_final.exception;

public class TelefoneUniqueViolationException extends RuntimeException{

	public TelefoneUniqueViolationException(String message) {
        super(message);
    }
	
}