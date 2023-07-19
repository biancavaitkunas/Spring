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

import br.com.trier.spring.domain.Championship;
import br.com.trier.spring.services.ChampionshipService;

@RestController
@RequestMapping(value = "/campeonato")
public class ChampionshipResource {
	
	@Autowired
	private ChampionshipService service;
	
	@PostMapping
	public ResponseEntity<Championship> insert (@RequestBody Championship championship) {
		Championship newChampionship = service.insert(championship);
		return newChampionship != null ? ResponseEntity.ok(newChampionship) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Championship> findById (@PathVariable Integer id) {
		Championship championship = service.findById(id);
		return championship != null ? ResponseEntity.ok(championship) : ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Championship>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/ano/{year}")
	public ResponseEntity<List<Championship>> findByYear(@PathVariable Integer year){
		return ResponseEntity.ok(service.findByYear(year));
	}
	
	@GetMapping("/descricao/{description}")
	public ResponseEntity<List<Championship>> findByDescricaoStartingWithIgnoreCase(@PathVariable String description){
		return ResponseEntity.ok(service.findByDescriptionStartingWithIgnoreCase(description));
	}
	
	@GetMapping("/anos/{inicialYear}/{finalYear}")
	public ResponseEntity<List<Championship>> findByYearBetween(@PathVariable Integer inicialYear, @PathVariable Integer finalYear){
		return ResponseEntity.ok(service.findByYearBetween(inicialYear, finalYear));
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Championship>> findByDescriptionContainingIgnoreCase(@RequestBody String description){
		return ResponseEntity.ok(service.findByDescriptionContainingIgnoreCase(description));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Championship> update (@PathVariable Integer id, @RequestBody Championship campeonato) {
		campeonato.setId(id);
		campeonato = service.update(campeonato);
		return campeonato != null ? ResponseEntity.ok(campeonato) : ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}

}
