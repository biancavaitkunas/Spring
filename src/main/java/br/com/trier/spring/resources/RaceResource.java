package br.com.trier.spring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import br.com.trier.spring.domain.dto.RaceDTO;
import br.com.trier.spring.services.ChampionshipService;
import br.com.trier.spring.services.RaceService;
import br.com.trier.spring.services.SpeedwayService;
import br.com.trier.spring.utils.DateUtils;

@RestController
@RequestMapping(value = "/corrida")
public class RaceResource {
	
	@Autowired
	private RaceService service;
	@Autowired
	private SpeedwayService pistaService;
	@Autowired
	private ChampionshipService campeonatoService;
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<RaceDTO> insert (@RequestBody RaceDTO raceDTO) {
		return ResponseEntity.ok(service.save(new Race(raceDTO, 
				pistaService.findById(raceDTO.getSpeedwayId()), 
				campeonatoService.findById(raceDTO.getChampionshipId())))
				.toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<RaceDTO> findById (@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id).toDTO());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<RaceDTO>> listarTodos(){
		return ResponseEntity.ok(service.listAll().stream().map((corrida) -> corrida.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/date")
	public ResponseEntity<List<RaceDTO>> findByDate(@RequestParam String date){
		return ResponseEntity.ok(service.findByDate(DateUtils.strToZonedDateTime(date)).stream().map((corrida) -> corrida.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/data/{inicialDate}/{finalDate}")
	public ResponseEntity<List<RaceDTO>> findByDateBetween(@PathVariable String inicialDate, @PathVariable String finalDate){
		return ResponseEntity.ok(service.findByDateBetween(DateUtils.strToZonedDateTime(inicialDate), DateUtils.strToZonedDateTime(finalDate)).stream().map((corrida) -> corrida.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/pista/{idPista}")
	public ResponseEntity<List<RaceDTO>> findBySpeedway(@PathVariable Integer idPista){
		return ResponseEntity.ok(service.findBySpeedway(pistaService.findById(idPista)).stream().map((corrida) -> corrida.toDTO()).toList());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/campeonato/{idCampeonato}")
	public ResponseEntity<List<RaceDTO>> findByChampionship(@PathVariable Integer idCampeonato){
		return ResponseEntity.ok(service.findByChampionship(campeonatoService.findById(idCampeonato)).stream().map((corrida) -> corrida.toDTO()).toList());
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<RaceDTO> update (@PathVariable Integer id, @RequestBody RaceDTO corridaDTO) {
		pistaService.findById(corridaDTO.getSpeedwayId());
		campeonatoService.findById(corridaDTO.getChampionshipId());
		Race corrida = new Race();
		corrida.setId(id);
		return ResponseEntity.ok(service.save(new Race(corridaDTO, 
				pistaService.findById(corridaDTO.getSpeedwayId()), 
				campeonatoService.findById(corridaDTO.getChampionshipId())))
				.toDTO());
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
		
	}

}
