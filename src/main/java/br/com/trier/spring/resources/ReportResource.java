package br.com.trier.spring.resources;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring.domain.Race;
import br.com.trier.spring.domain.Country;
import br.com.trier.spring.domain.Speedway;
import br.com.trier.spring.domain.dto.RaceDTO;
import br.com.trier.spring.domain.dto.RaceCountryYearDTO;
import br.com.trier.spring.services.RaceService;
import br.com.trier.spring.services.CountryService;
import br.com.trier.spring.services.SpeedwayService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;

@RestController
@RequestMapping("/reports")
public class ReportResource {
	
	@Autowired
	private CountryService countryService;
	@Autowired
	private SpeedwayService speedwayService;
	@Autowired
	private RaceService raceService;
	
	
	@GetMapping("/races-by-coutry-year/{pais}/{ano}")
	public ResponseEntity<RaceCountryYearDTO> findByRaceCountryAndYear(@PathVariable Integer paisId, @PathVariable Integer ano){
		Country country = countryService.findById(paisId);
		List<Speedway> countrySpeedway = speedwayService.findByCountryOrderBySizeDesc(country);
		List<RaceDTO> raceDTO = countrySpeedway.stream().flatMap(speedway -> {
			try {
				return raceService.findBySpeedway(speedway).stream();
			} catch (ObjectNotFound e) {
				return Stream.empty();
			}
			}).filter((race) -> race.getDate().getYear() == ano)
				.map(Race :: toDTO).toList();

		return ResponseEntity.ok(new RaceCountryYearDTO(ano, country.getName(), raceDTO));
		
	}

}
