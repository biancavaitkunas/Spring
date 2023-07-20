package br.com.trier.spring.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring.domain.User;

@RestController
@RequestMapping(value = "/user")

public class UserResource {
	
	List <User> lista = new ArrayList<User>();
	
	public UserResource() {
		lista.add(new User (1,"Usuário 1", "usuario1@gmail.com", "123"));
		lista.add(new User (2,"Usuário 2", "usuario2@gmail.com", "456"));
		lista.add(new User (3,"Usuário 3", "usuario3@gmail.com", "789"));
	}
	
	@GetMapping
	public List<User> listAll() {
		return lista;
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<User>findById(@PathVariable (name = "codigo") Integer codigo) {
		User u = lista.stream().filter(user -> user.getId().equals(codigo)).findAny().orElse(null);
		return u != null ? ResponseEntity.ok(u): ResponseEntity.noContent().build();
		
	}
	
	@PostMapping
	public User insert (@RequestBody User u) {
		u.setId(lista.size() + 1);//alterar codigo requerido p 1 a frente
		lista.add(u);
		return u;
	}

}
