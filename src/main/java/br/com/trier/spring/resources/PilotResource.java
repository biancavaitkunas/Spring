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
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring.domain.Pilot;
import br.com.trier.spring.domain.dto.PilotDTO;
import br.com.trier.spring.services.TeamService;
import br.com.trier.spring.services.CountryService;
import br.com.trier.spring.services.PilotService;

@RestController
@RequestMapping(value = "/piloto")
public class PilotResource {
	
	@Autowired
	private PilotService service;
	@Autowired
	private CountryService countryService;
	@Autowired
	private TeamService teamService;
	
	@PostMapping
	public ResponseEntity<PilotDTO> insert (@RequestBody PilotDTO pilotDTO) {
		return ResponseEntity.ok(service.save(new Pilot(pilotDTO.getId(),
				pilotDTO.getName(), 
				countryService.findById(pilotDTO.getCountry().getId()), 
				teamService.findById(pilotDTO.getTeam().getId()))).ToDTO());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PilotDTO> findById (@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).ToDTO());
	}
	
	@GetMapping
	public ResponseEntity<List<PilotDTO>> listAll(){
		return ResponseEntity.ok(service.listAll().stream().map((pilot) -> pilot.ToDTO()).toList());
	}
	
	@GetMapping("/nome/{name}")
	public ResponseEntity<List<PilotDTO>> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name).stream().map((pilot) -> pilot.ToDTO()).toList());
	}
	
	@GetMapping("/pais/{idPais}")
	public ResponseEntity<List<PilotDTO>> findByCountry(@PathVariable Integer idCountry){
		return ResponseEntity.ok(service.findByCountry(countryService.findById(idCountry)).stream().map((piloto) -> piloto.ToDTO()).toList());
	}
	
	@GetMapping("/equipe/{idEquipe}")
	public ResponseEntity<List<PilotDTO>> findByTeam(@PathVariable Integer idTeam){
		return ResponseEntity.ok(service.findByTeam(teamService.findById(idTeam)).stream().map((piloto) -> piloto.ToDTO()).toList());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PilotDTO> update (@PathVariable Integer id, @RequestBody PilotDTO pilotoDTO) {
		Pilot pilot = new Pilot(pilotoDTO);
		pilot.setId(id);
		return ResponseEntity.ok(service.update(pilot).ToDTO());
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
		
	}

}


