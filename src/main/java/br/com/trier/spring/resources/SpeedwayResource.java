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

import br.com.trier.spring.domain.Speedway;
import br.com.trier.spring.services.CountryService;
import br.com.trier.spring.services.SpeedwayService;

@RestController
@RequestMapping(value = "/pista")
public class SpeedwayResource {
	
	@Autowired
	private SpeedwayService service;
	@Autowired
	private CountryService countryService;
	
	@PostMapping
	public ResponseEntity<Speedway> insert (@RequestBody Speedway speedway) {
		countryService.findById(speedway.getCountry().getId());//verificacao se o pais existe
		return ResponseEntity.ok(service.insert(speedway));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Speedway> findById (@PathVariable Integer id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Speedway>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/tamanho/{inicialSize}/{finalSize}")
	public ResponseEntity<List<Speedway>> findBySizeBetween(@PathVariable Integer inicalSize, @PathVariable Integer finalSize){
		return ResponseEntity.ok(service.findBySizeBetween(inicalSize, finalSize));
	}
	
	@GetMapping("/pais/{idpais}")
	public ResponseEntity<List<Speedway>> findByCountryOrderBySizeDesc(@PathVariable Integer idSpeedway){
		return ResponseEntity.ok(service.findByCountryOrderBySizeDesc(countryService.findById(idSpeedway)));
	}
	
	@GetMapping("/nome/{name}")
	public ResponseEntity<List<Speedway>> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Speedway> update (@PathVariable Integer id, @RequestBody Speedway speedway) {
		speedway.setId(id);
		return ResponseEntity.ok(service.update(speedway));
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
		
	}

}

