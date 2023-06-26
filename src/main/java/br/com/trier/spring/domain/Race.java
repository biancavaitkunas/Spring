package br.com.trier.spring.domain;

import java.time.ZonedDateTime;

import br.com.trier.spring.domain.dto.RaceDTO;
import br.com.trier.spring.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "corrida")
public class Race {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column(name = "id_corrida")
	@Setter
	private Integer id;
	
	@Column(name = "date")
	private ZonedDateTime date;
	
	@ManyToOne
	@NotNull
	private Speedway speedway;
	
	@ManyToOne
	private Championship championship;
	
	public Race (RaceDTO dto) {
		this(dto.getId(), DateUtils.strToZonedDateTime(dto.getDate()), 
				new Speedway(dto.getSpeedwayId(), dto.getSpeedwayName(), null, null), 
				new Championship(dto.getChampionshipId(), dto.getChampionshipDescription(), null));
	}
	
	public Race (RaceDTO dto, Speedway pista, Championship campeonato) {
		this(dto.getId(), DateUtils.strToZonedDateTime(dto.getDate()), pista, campeonato);
	}
	
	public RaceDTO toDTO() {
		return new RaceDTO(id, DateUtils.ZonedDateTimeToStr(date), speedway.getId(), speedway.getName(), championship.getId(), championship.getDescription());
		
	}

}
