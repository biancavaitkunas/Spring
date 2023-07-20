package br.com.trier.spring.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.domain.Team;
import br.com.trier.spring.domain.Country;
import br.com.trier.spring.domain.Pilot;
import br.com.trier.spring.repositories.PilotRepository;
import br.com.trier.spring.services.PilotService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import br.com.trier.spring.services.exceptions.IntegrityViolation;

@Service
public class PilotServiceImpl implements PilotService{
	
	@Autowired
	PilotRepository repository;
	
	private void validatePiloto(Pilot piloto) {
		if (piloto.getName() == null) {
			throw new IntegrityViolation("O nome não pode ser nulo");
		}
	}

	@Override
	public Pilot save(Pilot piloto) {
		validatePiloto(piloto);
		return repository.save(piloto);
	}

	@Override
	public List<Pilot> listAll() {
		return repository.findAll();
	}

	@Override
	public Pilot findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Piloto %s não encontrado".formatted(id)));
	}

	@Override
	public Pilot update(Pilot piloto) {
		findById(piloto.getId());
		validatePiloto(piloto);
		return repository.save(piloto);
	}

	@Override
	public void delete(Integer id) {
		Pilot piloto = findById(id);
		repository.delete(piloto);
	}

	@Override
	public List<Pilot> findByNameStartingWithIgnoreCase(String name) {
		List<Pilot> lista = repository.findByNameStartingWithIgnoreCase(name);
		if(lista.size() == 0) {
			throw new ObjectNotFound("Nome %s não encontrado".formatted(name));
		}
		return lista;
	}

	@Override
	public List<Pilot> findByCountry(Country pais) {
		List<Pilot> lista = repository.findByCountry(pais);
		if(lista.size() == 0) {
			throw new ObjectNotFound("Nenhum piloto encontrado para %s".formatted(pais));
		}
		return lista;
	}

	@Override
	public List<Pilot> findByTeam(Team equipe) {
		List<Pilot> lista = repository.findByTeam(equipe);
		if(lista.size() == 0) {
			throw new ObjectNotFound("Nenhum piloto encontrado para %s".formatted(equipe));
		}
		return lista;
	}

}
