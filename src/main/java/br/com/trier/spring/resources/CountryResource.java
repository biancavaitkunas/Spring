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

import br.com.trier.spring.domain.Country;
import br.com.trier.spring.services.CountryService;

@RestController
@RequestMapping (value = "/pais")
public class CountryResource {
	
	@Autowired
	private CountryService service;
	
	@PostMapping
	public ResponseEntity<Country> insert(@RequestBody Country country){
		Country newCountry = service.insert(country);
		return newCountry != null  ? ResponseEntity.ok(newCountry) : ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <Country> findById (@PathVariable Integer id) {
		Country country = service.findById(id);
		return country != null ? ResponseEntity.ok(country) : ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity <List<Country>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Country> update (@PathVariable Integer id, @RequestBody Country pais) {
		pais.setId(id);
		pais = service.update(pais);
		return pais != null ? ResponseEntity.ok(pais): ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Country>> findByNameStartingWithIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameStartingWithIgnoreCase(name));
	}

}
