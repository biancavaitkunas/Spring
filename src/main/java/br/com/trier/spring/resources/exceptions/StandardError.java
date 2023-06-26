package br.com.trier.spring.resources.exceptions;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StandardError {
	
	private ZonedDateTime time;
	private Integer status;
	private String erro;
	private String url;

}
