package br.com.trier.spring.services;

import java.util.List;

import br.com.trier.spring.domain.Team;
import br.com.trier.spring.domain.Country;
import br.com.trier.spring.domain.Pilot;

public interface PilotService {
	
	Pilot save (Pilot piloto);
	List<Pilot> listAll();
	Pilot findById(Integer id);
	Pilot update(Pilot piloto);
	void delete(Integer id);
	List<Pilot> findByNameStartingWithIgnoreCase(String name);
	List<Pilot> findByCountry(Country pais);
	List<Pilot> findByTeam(Team equipe);

}
