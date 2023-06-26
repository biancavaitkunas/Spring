package br.com.trier.spring.services.impl;

import java.time.ZonedDateTime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.domain.Championship;
import br.com.trier.spring.domain.Race;
import br.com.trier.spring.domain.Speedway;
import br.com.trier.spring.repositories.RaceRepository;
import br.com.trier.spring.services.RaceService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import br.com.trier.spring.services.exceptions.IntegrityViolation;

@Service
public class RaceServiceImpl implements RaceService{
	
	@Autowired
	RaceRepository repository;
	
	private void validateCorrida(Race corrida) {
		if(corrida.getDate() == null) {
			throw new IntegrityViolation("Data não pode ser nula");
		}
		if (corrida.getDate().getYear() != corrida.getChampionship().getYear()) {
			throw new IntegrityViolation("Data não pode diferir da data do campeonato");
		}
	}

	@Override
	public Race save(Race corrida) {
		validateCorrida(corrida);
		return repository.save(corrida);
	}

	@Override
	public List<Race> listAll() {
		return repository.findAll();
	}

	@Override
	public Race findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Corrida %s não encontrada".formatted(id)));
	}

	@Override
	public Race update(Race corrida) {
		findById(corrida.getId());
		validateCorrida(corrida);
		return repository.save(corrida);
	}

	@Override
	public void delete(Integer id) {
		Race corrida = findById(id);
		repository.delete(corrida);
		
	}

	@Override
	public List<Race> findByDate(ZonedDateTime date) {
		List<Race> lista = repository.findByDate(date);
		if (lista.size() == 0) {
			throw new ObjectNotFound("Nenhuma corrida na data %s".formatted(date));
		}
		return lista;
	}

	@Override
	public List<Race> findByDateBetween(ZonedDateTime inicialDate, ZonedDateTime finalDate) {
		List<Race> lista = repository.findByDateBetween(inicialDate, finalDate);
		if (lista.size() == 0) {
			throw new ObjectNotFound("Nenhuma corrida neste período");
		}
		return lista;
	}

	@Override
	public List<Race> findBySpeedway(Speedway speedway) {
		List<Race> lista = repository.findBySpeedway(speedway);
		if (lista.size() == 0) {
			throw new ObjectNotFound("Nenhuma corrida na pista %s".formatted(speedway));
		}
		return lista;
	}

	@Override
	public List<Race> findByChampionship(Championship campeonato) {
		List<Race> lista = repository.findByChampionship
				(campeonato);
		if (lista.size() == 0) {
			throw new ObjectNotFound("Nenhuma corrida no campeonato %s".formatted(campeonato));
		}
		return lista;
	}	

}
