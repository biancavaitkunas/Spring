package br.com.trier.spring.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.domain.Team;
import br.com.trier.spring.repositories.TeamRepository;
import br.com.trier.spring.services.TeamService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import br.com.trier.spring.services.exceptions.IntegrityViolation;

@Service
public class TeamServiceImpl implements TeamService{
	
	@Autowired
	TeamRepository repository;

	@Override
	public Team insert(Team team) {
		findByName(team);
		return repository.save(team);
	}

	@Override
	public List<Team> listAll() {
		return repository.findAll();
	}

	@Override
	public Team findById(Integer id) {
		Optional<Team> obj = repository.findById(id);
		return obj.orElseThrow(()-> new ObjectNotFound("Equipe %s não encontrada".formatted(id)));
	}

	@Override
	public Team update(Team team) {
		findByName(team);
		return repository.save(team);
	}

	@Override
	public void delete(Integer id) {
		Team team = findById(id);
		if (team != null) {
			repository.delete(team);
		}
	}
	
	private void findByName(Team obj) {
		Team team = repository.findByName(obj.getName());
		if (team != null && team.getId() != obj.getId()) {
			throw new IntegrityViolation("Equipe %s já existe".formatted(obj.getName()));
		}
	}

	@Override
	public List<Team> findByNameStartingWithIgnoreCase(String name) {
		List<Team> list = repository.findByNameStartingWithIgnoreCase(name.substring(0));
		if (list.size() == 0) {
			throw new ObjectNotFound("Não há equipes que começem com a letra %s".formatted(name));
		}
		return list;
	}

}
