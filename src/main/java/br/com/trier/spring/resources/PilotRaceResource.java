package br.com.trier.spring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring.domain.Race;
import br.com.trier.spring.domain.Pilot;
import br.com.trier.spring.domain.PilotRace;
import br.com.trier.spring.domain.dto.RaceDTO;
import br.com.trier.spring.domain.dto.PilotRaceDTO;
import br.com.trier.spring.services.RaceService;
import br.com.trier.spring.services.PilotRaceService;
import br.com.trier.spring.services.PilotService;
import br.com.trier.spring.utils.DateUtils;

@RestController
@RequestMapping(value = "/pilotoCorrida")
public class PilotRaceResource {
	
	@Autowired
	PilotRaceService service;
	@Autowired
	PilotService pilotService;
	@Autowired
	RaceService raceService;
	
	@PostMapping
	public ResponseEntity<PilotRaceDTO> insert (@RequestBody PilotRaceDTO pilotoCorridaDTO) {
		return ResponseEntity.ok(service.insert(new PilotRace(pilotoCorridaDTO, 
				pilotService.findById(pilotoCorridaDTO.getPilotId()), 
				raceService.findById(pilotoCorridaDTO.getRaceId())))
				.toDTO());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PilotRaceDTO> findById (@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@GetMapping
	public ResponseEntity<List<PilotRaceDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream().map((pilotoCorrida) -> pilotoCorrida.toDTO()).toList());
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<PilotRaceDTO> update (@PathVariable Integer id, @RequestBody PilotRaceDTO pilotRaceDTO) {
		pilotService.findById(pilotRaceDTO.getPilotId());
		raceService.findById(pilotRaceDTO.getRaceId());
		PilotRace pilotoCorrida = new PilotRace();
		pilotoCorrida.setId(id);
		return ResponseEntity.ok(service.insert(new PilotRace(pilotRaceDTO, 
				pilotService.findById(pilotRaceDTO.getPilotId()), 
				raceService.findById(pilotRaceDTO.getRaceId())))
				.toDTO());
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
		
	}
	
	@GetMapping("piloto/{piloto}")
	public ResponseEntity<List<PilotRaceDTO>> findByPiloto(@PathVariable Integer pilotoId){
		return ResponseEntity.ok(service.findByPilot(pilotService.findById(pilotoId)).stream().map((piloto) -> piloto.toDTO()).toList());
	}

}
