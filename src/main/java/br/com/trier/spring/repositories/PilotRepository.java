package br.com.trier.spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring.domain.Team;
import br.com.trier.spring.domain.Country;
import br.com.trier.spring.domain.Pilot;

@Repository
public interface PilotRepository extends JpaRepository<Pilot, Integer>{
	
	List<Pilot> findByName(String name);
	List<Pilot> findByCountry(Country country);
	List<Pilot> findByTeam(Team team);

}
