package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.domain.Championship;
import br.com.trier.spring.domain.Team;

public interface ChampionshipService {
	
	Championship insert(Championship championship);
	List<Championship> listAll();
	Championship findById(Integer id);
	Championship update(Championship championship);
	void delete(Integer id);
	List <Championship> findByDescriptionStartingWithIgnoreCase(String description);
	List<Championship> findByYear(Integer inicialYear);
	List<Championship> findByDescriptionContainingIgnoreCase(String description);
	List<Championship> findByYearBetween(Integer inicialYear, Integer finalYear);

}
