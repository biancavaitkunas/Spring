package br.com.trier.spring.services.impl;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.domain.User;
import br.com.trier.spring.repositories.UserRepository;
import br.com.trier.spring.services.UserService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import br.com.trier.spring.services.exceptions.IntegrityViolation;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository repository;

	@Override
	public User salvar(User user) {
		findByEmail(user);
		return repository.save(user);
	}

	@Override
	public List<User> listAll() {
		if (repository.findAll().size() == 0) {
			throw new ObjectNotFound("Nenhum usuário encontrado");
		}
		return repository.findAll();
	}

	@Override
	public User findById(Integer id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFound("Usuário %s não encontrado".formatted(id)));
	}

	@Override
	public User update(User user) {
		findByEmail(user);
		return repository.save(user);
	}

	@Override
	public void delete(Integer id) {
		User user = findById(id);
		repository.delete(user);
	}

	@Override
	public List<User> findByNameStartingWithIgnoreCase(String name) {
		List<User> lista = repository.findByNameStartingWithIgnoreCase(name);
		if (lista.size() == 0) {
			throw new ObjectNotFound("Nenhum nome de usuário inicia com %s".formatted(name));
		}
		return lista;
	}
	
	private void findByEmail(User obj) {
		User busca = repository.findByEmail(obj.getEmail()).orElse(null);
		if (busca != null && !busca.getId().equals(obj.getId())) {
			throw new IntegrityViolation("Email %s já existe".formatted(obj.getEmail()));
		}
		
		/*Optional<User> user = repository.findByEmail(obj.getEmail());
		if (user != null && user.get().getId() != obj.getId()) {
			throw new IntegrityViolation("Email %s já existe".formatted(obj.getEmail()));
		}*/
	}

}
