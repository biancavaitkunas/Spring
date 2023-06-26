package br.com.trier.spring.services.impl;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring.domain.Championship;
import br.com.trier.spring.repositories.ChampionshipRepository;
import br.com.trier.spring.services.ChampionshipService;
import br.com.trier.spring.services.exceptions.ObjectNotFound;
import br.com.trier.spring.services.exceptions.IntegrityViolation;


@Service
public class ChampionshipServiceImpl implements ChampionshipService{
	
	@Autowired
	ChampionshipRepository repository;

	@Override
	public Championship insert(Championship championship) {
		validateChampionship(championship);
		return repository.save(championship);
	}

	@Override
	public List<Championship> listAll() {
		if(repository.findAll().size() == 0) {
			throw new ObjectNotFound("Nenhum campeonato encontrado");
		}
		return repository.findAll();
	}

	@Override
	public Championship findById(Integer id) {
		Optional<Championship> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFound("Campeonato %s não encontrado".formatted(id)));
	}

	@Override
	public Championship update(Championship championship) {
		validateChampionship(championship);
		return repository.save(championship);
	}

	@Override
	public void delete(Integer id) {
		Championship championship = findById(id);
		if (championship != null) {
			repository.delete(championship);
		}
	}	

	@Override
	public List<Championship> findByYear(Integer year) {
		List<Championship> list = repository.findByYear(year);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum campeonato no ano %s".formatted(year));
		}
		return list;
	}

	@Override
	public List<Championship> findByDescriptionStartingWithIgnoreCase(String description) {
		List<Championship> list = repository.findByDescriptionStartingWithIgnoreCase(description);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum campeonato inicia com %s".formatted(description));
		}
		return list;
	}
	
	@Override
	public List<Championship> findByYearBetween(Integer inicialYear, Integer finalYear) {
		List<Championship> list = repository.findByYearBetween(inicialYear, finalYear);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum campeonato encontrado neste período");
		}
		return list;
	}
	
	@Override
	public List<Championship> findByDescriptionContainingIgnoreCase(String description) {
		List<Championship> list = repository.findByDescriptionContainingIgnoreCase(description.substring(0));
		if (list.size() == 0) {
			throw new ObjectNotFound("Não há descrições %s".formatted(description));
		}
		return list;
	}
	
	private void validateYear(Championship campeonato) {
		int anoAtual = LocalDate.now().getYear();
		if (campeonato.getYear()<= 1990 || campeonato.getYear()> anoAtual+1) {
			throw new IntegrityViolation("Ano %s é inválido para o campeonato".formatted(campeonato.getYear()));
		}
	}
	

	private void validateChampionship(Championship championship) {
		if (championship == null) {
			throw new IntegrityViolation("Campeonato nulo");
		}
		if (championship.getDescription() == null || championship.getDescription().equals(" ")) {
			throw new IntegrityViolation("Descrição obrigatória");
		}
		validateYear(championship);
	}


}
