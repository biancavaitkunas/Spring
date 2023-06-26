package br.com.trier.spring.services;

import java.time.ZonedDateTime;
import java.util.List;

import br.com.trier.spring.domain.Championship;
import br.com.trier.spring.domain.Race;
import br.com.trier.spring.domain.Speedway;

public interface RaceService {
	
	Race save (Race corrida);
	List<Race> listAll();
	Race findById(Integer id);
	Race update(Race corrida);
	void delete(Integer id);
	List<Race> findByDate(ZonedDateTime date);
	List<Race> findByDateBetween(ZonedDateTime inicialDate, ZonedDateTime finalDate);
	List<Race> findBySpeedway(Speedway pista);
	List<Race> findByChampionship(Championship championship);
	

}
