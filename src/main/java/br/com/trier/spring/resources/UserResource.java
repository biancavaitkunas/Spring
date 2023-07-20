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
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring.domain.User;
import br.com.trier.spring.domain.dto.UserDTO;
import br.com.trier.spring.services.UserService;

@RestController
@RequestMapping(value = "/usuario")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	//@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<UserDTO> insert (@RequestBody UserDTO user) {
		User newUser = service.salvar(new User(user));
		return newUser != null ? ResponseEntity.ok(newUser.ToDto()) : ResponseEntity.badRequest().build();
		 
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> findById (@PathVariable Integer id) {
		User user = service.findById(id);
		return ResponseEntity.ok(user.ToDto());
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<UserDTO>> listarTodos(){
		return ResponseEntity.ok(service.listAll().stream().map((user) -> user.ToDto()).toList());
	}
	
	//@Secured({"ROLE_USER"})
	@GetMapping("/name/{name}")
	public ResponseEntity<List<UserDTO>> findByNameStartingWithIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameStartingWithIgnoreCase(name).stream().map((user) -> user.ToDto()).toList());
	}
	
	//@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> update (@PathVariable Integer id, @RequestBody UserDTO userDTO) {
		User user = new User(userDTO);
		user.setId(id);
		user = service.update(user);
		return user != null ? ResponseEntity.ok(user.ToDto()) : ResponseEntity.badRequest().build();
		
	}
	
	//@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete (@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.ok().build();
		
	}
	
	

}
