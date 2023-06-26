package br.com.trier.spring.services;

import java.util.List;


import br.com.trier.spring.domain.Team;

public interface TeamService {
	
	Team insert(Team team);
	List<Team> listAll();
	Team findById(Integer id);
	Team update(Team team);
	void delete(Integer id);
	List <Team> findByNameStartingWithIgnoreCase(String name);

}
