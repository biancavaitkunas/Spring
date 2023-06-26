package br.com.trier.spring.domain;

import br.com.trier.spring.domain.dto.PilotRaceDTO;

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
@Entity (name = "pilotoCorrida")
public class PilotRace {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pilotoCorrida")
	@Setter
	private Integer id;
	
	@Column(name = "colocacao")
	private Integer placing;
	
	@ManyToOne
	private Pilot pilot;
	
	@ManyToOne
	private Race race;
	
	public PilotRace (PilotRaceDTO dto) {
		this(dto.getId(), dto.getPlacing(), 
				new Pilot(dto.getPilotId(), dto.getPilotName(), null, null), 
				new Race(dto.getRaceId(), null, null, null));
	}
	
	public PilotRace (PilotRaceDTO dto, Pilot pilot, Race race) {
		this(dto.getId(), dto.getPlacing(), pilot, race);
	}
	
	public PilotRaceDTO toDTO() {
		return new PilotRaceDTO(id, placing, pilot.getId(), pilot.getName(), race.getId());
		
	}

}
