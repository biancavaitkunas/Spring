package br.com.trier.spring.services.exceptions;

public class IntegrityViolation extends RuntimeException{
	
	public IntegrityViolation (String mensagem) {
		super(mensagem);
	}

}
