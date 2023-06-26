package br.com.trier.spring.resources.exceptions;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.trier.spring.services.exceptions.ObjectNotFound;
import br.com.trier.spring.services.exceptions.IntegrityViolation;
import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFound.class)//mapeado para a classe objNaoEncontrado, ExceptionHandler é um listener
	public ResponseEntity<StandardError> objetoNaoEncontrado (ObjectNotFound ex, HttpServletRequest request) {
		StandardError erro = new StandardError(ZonedDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(IntegrityViolation.class)
	public ResponseEntity<StandardError> violacaoIntegridade (IntegrityViolation ex, HttpServletRequest request) {
		StandardError erro = new StandardError(ZonedDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);//body: json, o que passa no postman para acadastrar 
	}


}
