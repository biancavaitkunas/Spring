package br.com.trier.spring.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RaceCountryYearDTO {
	
	private Integer ano;
	private String pais;
	private List<RaceDTO>corridas;

}
