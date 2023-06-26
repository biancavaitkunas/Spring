package br.com.trier.spring.domain.dto;

import br.com.trier.spring.domain.Team;
import br.com.trier.spring.domain.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PilotDTO {
	
	private Integer id;
	private String name;
	private Country country;
	private Team team;

}
