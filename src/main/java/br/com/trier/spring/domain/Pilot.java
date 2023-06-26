package br.com.trier.spring.domain;

import br.com.trier.spring.domain.dto.PilotDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "piloto")
public class Pilot {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "id_piloto")
	@Setter
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne
	private Country country;
	
	@ManyToOne
	private Team team;
	
	public Pilot(PilotDTO dto) {
		this(dto.getId(), dto.getName(), dto.getCountry(), dto.getTeam());
	}
	
	public PilotDTO ToDTO() {
		return new PilotDTO(this.id, this.name, this.country, this.team);
	}

}
